package com.credithc.cache.impl.defau1t.description;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

/**
 * checksum Util
 * 
 * @author sai.zhang
 * 
 */
public class CacheChecksumHelper {

	protected static Logger logger = LoggerFactory.getLogger(CacheChecksumHelper.class);

	public static int COMPARE_EQUALS = 0;

	public static int COMPARE_LESS = -1;

	public static int COMPARE_GREATE = 1;

	protected static String CHECK_SUM_TEMPLATE = "%s_%s";

	protected static String CHECK_SUM_SPLITER = "_";

	protected static String CHECK_SUM_DATEPARTTERN = "yyyyMMddHHmmss";
	
	protected static String[] CHECK_SUM_DATEPARTTERN_ARY = new String[]{"yyyyMMddHHmmss"};

	protected static Comparator<String> FIELDS_SORTER = new Comparator<String>() {

		public int compare(String lhs, String rhs) {
			return lhs.compareTo(rhs);
		}
	};

	/**
	 * 计算cache entity checksum CheckSum规则：
	 * 
	 * @param entityName
	 * @return
	 */
	public static String calculateChecksum(Class<?> entityClass) {
		String clazzFileCreateTime = null;
		String clazzSignature = null;
		try {
			logger.debug("Cache  calculate checksum [ " + entityClass.getName() + " ]");

			clazzFileCreateTime = getLastModifiedDate(entityClass);
			Field[] fields = entityClass.getDeclaredFields();
			clazzSignature = fieldsSignature(fields);
			return String.format(CHECK_SUM_TEMPLATE, clazzFileCreateTime, clazzSignature);
		} catch (Exception e) {
			throw new RuntimeException("Cache Error : calculate entity["
					+ entityClass.getName() + "] Checksum happend error", e);
		}

	}

	/**
	 * 比较两个check sum 比较策略： 1.首先判断checksum是否一致 2.如果不一致比较两个日期
	 * 
	 * @param srcCs
	 * @param desCs
	 * @return
	 * @throws ParseException 
	 */
	public static boolean isNeedForceUpdate(String localCs, String cachedCs) {
		if (logger.isDebugEnabled()) {
			logger.debug("localCs [ " + localCs + " ]");
			logger.debug("cachedCs [ " + cachedCs + " ]");
		}

		if (StringUtils.isEmpty(localCs) || StringUtils.isEmpty(cachedCs)) {
			return StringUtils.isEmpty(localCs) ? false : true;
		}
		String[] srcCsList = localCs.split(CHECK_SUM_SPLITER);
		String[] desCsList = cachedCs.split(CHECK_SUM_SPLITER);
		// 比较字段签名是否一致
		if (srcCsList[1].equals(desCsList[1])) {
			return false;
		}
		try {
			Date localDate = /*DateUtil.parse(CHECK_SUM_DATEPARTTERN, srcCsList[0]);*/DateUtils.parseDate(srcCsList[0], CHECK_SUM_DATEPARTTERN_ARY);
			Date cachedDate = /*DateUtil.parse(CHECK_SUM_DATEPARTTERN, desCsList[0]);*/DateUtils.parseDate(desCsList[0], CHECK_SUM_DATEPARTTERN_ARY);
			return localDate.compareTo(cachedDate) == 1;
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 根据class name 获取class file的最后修改时间
	 * 
	 * @param entityClass
	 * @return
	 * @throws IOException
	 */
	private static String getLastModifiedDate(Class<?> entityClass) throws IOException {
		String path = "/" + entityClass.getName().replaceAll("\\.", "/") + ".class";
		URL url = entityClass.getResource(path);
		Date lastModifiedDate = new Date(url.openConnection().getLastModified());
		return /*DateUtil.format(CHECK_SUM_DATEPARTTERN, lastModifiedDate);*/DateFormatUtils.format(lastModifiedDate, CHECK_SUM_DATEPARTTERN);
	}

	/**
	 * 计算类变量的MD5
	 * 
	 * @param fields
	 * @return
	 */
	private static String fieldsSignature(Field[] fields) {
		List<String> stringList = new ArrayList<String>();
		for (Field f : fields) {
			stringList.add(f.getName() + CHECK_SUM_SPLITER
					+ f.getGenericType().toString());
		}
		Collections.sort(stringList, FIELDS_SORTER);
		StringBuffer signatureStr = new StringBuffer();
		for (String str : stringList) {
			signatureStr.append(str);
			signatureStr.append("|");
		}
		if (logger.isDebugEnabled())
			logger.debug("Cache fields Signature [ " + signatureStr.toString() + " ]");
		return /*MD5.md5Hex16(signatureStr.toString(), "UTF-8");*/md5Hex16(signatureStr.toString(), "UTF-8");
	}
	
	private static String md5Hex16(String msgStr, String charset){
		byte[] msg = msgStr.getBytes(Charset.forName(charset));
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");

			byte[] md5 = md.digest(msg);
			md5 = subBytes(md5, 4, 12);

			return new String(Hex.encodeHex(md5, false));
		} catch (Exception e) {
			throw new RuntimeException("Calc md5 error", e);
		}
	}
	
	private static byte[] subBytes(byte[] bytes, int start, int end) {
		if (bytes == null) {
			return null;
		}

		if (bytes.length == 0) {
			return new byte[0];
		}

		if (start < 0) {
			start = Math.max(0, bytes.length + start); // 负无穷大取0
		}

		if (end <= 0) {
			end = bytes.length + end;
		}

		end = Math.min(end, bytes.length); // 正无穷大取length

		if (start >= bytes.length || end > bytes.length || start >= end) {
			return new byte[0];
		}

		byte[] subBytes = new byte[end - start];
		System.arraycopy(bytes, start, subBytes, 0, end - start);

		return subBytes;
	}
}
