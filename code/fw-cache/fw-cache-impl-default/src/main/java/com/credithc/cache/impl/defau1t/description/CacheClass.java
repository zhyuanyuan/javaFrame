package com.credithc.cache.impl.defau1t.description;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.aop.TargetClassAware;

import com.credithc.cache.common.annotation.Cache;
import com.credithc.cache.common.annotation.CacheEvict;
import com.credithc.cache.common.annotation.CacheEviction;
import com.credithc.cache.common.annotation.CacheKey;
import com.credithc.cache.common.annotation.CacheObject;
import com.credithc.cache.common.annotation.NotDefine;

/**
 * 缓存类。
 * 
 * @author sai.zhang
 */
public class CacheClass {
	private Pattern cacheKeyPattern = Pattern
			.compile("^([^=]*)=?\\s*args\\[(\\s*[0-9]+\\s*)\\]\\.?(.*)$");

	/**
	 * 类
	 */
	private Class<?> clazz;

	/**
	 * 接口类集
	 */
	private Class<?>[] interfaces;

	/**
	 * 缓存方法集
	 */
	private Map<String, CacheMethod> cacheMethods = new HashMap<String, CacheMethod>();

	private CacheKeyDescription[] parseCacheKeys(String serviceId, Method method,
			String[] keys) {
		if (keys != null) {
			int i = 0;
			CacheKeyDescription[] ckDescs = new CacheKeyDescription[keys.length];
			for (String key : keys) {
				CacheKeyDescription ckDesc = new CacheKeyDescription();
				Matcher matcher = cacheKeyPattern.matcher(key.trim());
				boolean matchFound = matcher.find();
				if (matchFound) {
					String keyPrefix = StringUtils.trimToNull(matcher.group(1).trim());
					int argIdx = Integer.valueOf(matcher.group(2).trim(), 10);
					if (argIdx >= method.getParameterTypes().length) {
						throw new RuntimeException("Illegal argIdx=[" + argIdx
								+ "], , serviceId=[" + serviceId + "], method=["
								+ method.getName() + "].");
					}
					String jxpathAccessor = StringUtils.trimToNull(matcher.group(3)
							.trim());
					ckDesc.setPropertyName(keyPrefix);
					ckDesc.setArgIdx(argIdx);
					ckDesc.setJxpathAccessor(jxpathAccessor);
					ckDescs[i++] = ckDesc;
				} else {
					throw new RuntimeException("Illegal amqp address string.");
				}
			}

			return ckDescs;
		}

		return null;
	}

	private static void getAllInterfaces(Class<?> clazz, List<Class<?>> infClasses) {
		if (clazz.isInterface()) {
			infClasses.add(clazz);
			return;
		}

		for (Class<?> infClazz : clazz.getInterfaces()) {
			infClasses.add(infClazz);
		}

		if (!clazz.getSuperclass().equals(Object.class)) {
			getAllInterfaces(clazz.getSuperclass(), infClasses);
		}
	}

	public static boolean isCacheClass(Class<?> clazz) {
		List<Class<?>> infClasses = new ArrayList<Class<?>>();
		getAllInterfaces(clazz, infClasses);

		for (Class<?> infClazz : infClasses) {
			Method[] methodList = infClazz.getMethods();
			for (Method method : methodList) {
				Cache cache = method.getAnnotation(Cache.class);
				if (cache != null) {
					if (method.getReturnType() == null) {
						throw new RuntimeException(
								"Can't declare Cache to void return function, class=["
										+ clazz.getName() + "], method=["
										+ method.getName() + "].");
					}

					return true;
				}

				CacheEvict oCacheEvictAnno = method.getAnnotation(CacheEvict.class);
				if (oCacheEvictAnno != null) {
					return true;
				} else {
					CacheEviction cacheEvictionAnno = method
							.getAnnotation(CacheEviction.class);
					if (cacheEvictionAnno != null) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public CacheClass(Class<?> clazz) {
		this.clazz = clazz;

		List<Class<?>> infClasses = new ArrayList<Class<?>>();
		getAllInterfaces(clazz, infClasses);
		this.interfaces = new Class<?>[infClasses.size() + 1];

		this.interfaces[0] = TargetClassAware.class;
		for (int i = 1; i < infClasses.size() + 1; i++) {
			this.interfaces[i] = infClasses.get(i - 1);
		}

		for (Class<?> infClazz : infClasses) {
			Method[] methodList = infClazz.getMethods();
			for (Method method : methodList) {
				CacheMethodDescription cmDesc = new CacheMethodDescription();

				Cache cache = method.getAnnotation(Cache.class);
				if (cache != null) {
					if (method.getReturnType() == null) {
						throw new RuntimeException(
								"Can't declare Cache to void return function, class=["
										+ clazz.getName() + "], method=["
										+ method.getName() + "].");
					}

					cmDesc.setCacheFlag(true);
					cmDesc.setCacheTtl(cache.ttl());
					String entity = StringUtils.trimToNull(cache.entity());
					cmDesc.setCacheEntity(entity);
					cmDesc.setCacheNull(cache.cacheNull());
					String[] keys = cache.keys();
					cmDesc.setCacheKeyDescs(parseCacheKeys(clazz.getName(), method, keys));

					Class<?> returnType = method.getReturnType();
					CacheObject cacheObjAnno = returnType
							.getAnnotation(CacheObject.class);
					if (cacheObjAnno != null) {
						ReturnDescription retDesc = new ReturnDescription();
						String retEntity = StringUtils.trimToNull(cacheObjAnno.entity());
						if (retEntity != null) {
							retDesc.setEntity(retEntity);
						} else {
							retDesc.setEntity(returnType.getName());
						}
						// 新增cache checksum 字段
						cmDesc.setCahceEntityChecksum(CacheChecksumHelper
									.calculateChecksum(returnType));

						if (cmDesc.getCacheEntity() == null && cache.keys().length != 0) {
							// 如果使用了keys标签，并且返回对象为CacheObject，则缺省使用ReturnObject的Entity
							cmDesc.setCacheEntity(retDesc.getEntity());
						}

						retDesc.setTtl(cacheObjAnno.ttl());
						if (cmDesc.getCacheTtl() > cacheObjAnno.ttl()) {
							// 方法定义的ttl大于对象定义，对齐到对象ttl
							cmDesc.setCacheTtl(cacheObjAnno.ttl());
						}

						Field[] fields = returnType.getDeclaredFields();
						for (Field field : fields) {
							field.setAccessible(true);
							CacheKey cacheKeyAnno = field.getAnnotation(CacheKey.class);
							if (cacheKeyAnno != null) {
								ReturnDescription.KeyItem keyItem = new ReturnDescription.KeyItem();
								keyItem.setPropertyName(field.getName());
								keyItem.setJxpathAccessor(StringUtils.trimToNull(cacheKeyAnno.jxpathAccessor()));
								retDesc.getKeys().add(keyItem);
							}
						}

						if (retDesc.getKeys().isEmpty()) {
							// 找不到Key定义
							throw new RuntimeException(
									"Not found the cacheKey define in class=["
											+ returnType.getName() + "].");
						}

						cmDesc.setReturnDesc(retDesc);
					}

					if (cmDesc.getCacheEntity() == null) {
						// 缺省使用方法名称为Entity
						cmDesc.setCacheEntity(method.getDeclaringClass().getName() + "."
								+ method.getName());
					}
				}

				CacheEvict[] cacheEvicts = null;
				CacheEvict oCacheEvictAnno = method.getAnnotation(CacheEvict.class);
				if (oCacheEvictAnno != null) {
					cacheEvicts = new CacheEvict[1];
					cacheEvicts[0] = oCacheEvictAnno;
				} else {
					CacheEviction cacheEvictionAnno = method
							.getAnnotation(CacheEviction.class);
					if (cacheEvictionAnno != null) {
						cacheEvicts = cacheEvictionAnno.value();
					}
				}

				if (cacheEvicts != null) {
					CacheEvictionDescription cacheEvictionDesc = new CacheEvictionDescription();
					for (CacheEvict cacheEvictAnno : cacheEvicts) {
						CacheEvictionDescription.CacheEvictItem cacheEvictItem = new CacheEvictionDescription.CacheEvictItem();

						String entity = StringUtils.trimToNull(cacheEvictAnno.entity());

						if (entity == null) {
							Class<?> cacheObject = cacheEvictAnno.cacheObject();
							if (!cacheObject.equals(NotDefine.class)) {
								CacheObject cacheObjAnno = cacheObject
										.getAnnotation(CacheObject.class);
								if (cacheObjAnno != null) {
									entity = StringUtils.trimToNull(cacheObjAnno.entity());
									if (entity == null) {
										entity = cacheObject.getName();
									}
								}
							}
						}

						if (entity == null) {
							throw new RuntimeException(
									"Must define entity or cacheObject in CacheEvict annotation, class=["
											+ clazz.getName() + "], method=["
											+ method.getName() + "].");
						}

						cacheEvictItem.setEntity(entity);
						cacheEvictItem.setCacheKeyDesc(parseCacheKeys(clazz.getName(),
								method, cacheEvictAnno.keys()));

						cacheEvictionDesc.getCacheEvictItems().add(cacheEvictItem);
					}

					cmDesc.setCacheEvictionDesc(cacheEvictionDesc);
				}

				if (cmDesc.getCacheEvictionDesc() == null
						&& cmDesc.isCacheFlag() == false) {
					continue;
				}

				CacheMethod cMethod = new CacheMethod();
				cMethod.setMethod(method);
				cMethod.setClazz(this.clazz);
				cMethod.setDescription(cmDesc);

				cacheMethods.put(method.toGenericString(), cMethod);
			}
		}

		if (cacheMethods.isEmpty()) {
			throw new RuntimeException("The class=[" + clazz.getName()
					+ "] isn't the cache class.");
		}
	}

	public CacheMethod getCacheMethod(Method method) {
		return cacheMethods.get(method.toGenericString());
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Map<String, CacheMethod> getCacheMethods() {
		return cacheMethods;
	}

	public Class<?>[] getInterfaces() {
		return interfaces;
	}

}
