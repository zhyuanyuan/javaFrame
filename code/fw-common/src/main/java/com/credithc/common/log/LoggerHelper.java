package com.credithc.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerHelper {
	
	private Logger logger;
	
	private static final String NULL_OBJ_PLACE_HOLDER = "NULL";
	
	public static final String REQUEST_CHAIN_CTX_KEY = "REQUEST_CHAIN_CTX_KEY";
	
	public LoggerHelper(Class<?> clazz){
		logger = LoggerFactory.getLogger(clazz);
	}
	
	public void debug(LogableDTO obj, String str){
		logger.debug(getShortBizInfo(obj) + str);
	}
	
	public void debug(LogableDTO obj, String format, Object... arguments){
		logger.debug(getShortBizInfo(obj) + format, arguments);
	}
	
	public void debug(LogableDTO obj, String str, Throwable t){
		logger.debug(getShortBizInfo(obj) + str, t);
	}
	
	public void info(LogableDTO obj, String str){
		logger.info(getShortBizInfo(obj) + str);
	}
	
	public void info(LogableDTO obj, String format, Object... arguments){
		logger.info(getShortBizInfo(obj) + format, arguments);
	}
	
	public void info(LogableDTO obj, String str, Throwable t){
		logger.info(getShortBizInfo(obj) + str, t);
	}
	
	public void warn(LogableDTO obj, String str){
		logger.warn(getShortBizInfo(obj) + str);
	}
	
	public void warn(LogableDTO obj, String format, Object... arguments){
		logger.warn(getShortBizInfo(obj) + format, arguments);
	}
	
	public void warn(LogableDTO obj, String str, Throwable t){
		logger.warn(getShortBizInfo(obj) + str, t);
	}
	
	public void error(LogableDTO obj, String str){
		logger.error(getShortBizInfo(obj) + str);
	}
	
	public void error(LogableDTO obj, String format, Object... arguments){
		logger.error(getShortBizInfo(obj) + format, arguments);
	}
	
	public void error(LogableDTO obj, String str, Throwable t){
		logger.error(getShortBizInfo(obj) + str, t);
	}
	
	public static String getShortBizInfo(LogableDTO obj){
		if(obj != null){
			return "[" + obj.getSysId() + "|" + obj.getSysSeq() + "|" + obj.getBusinessId() + "|" + obj.getRequestChain() + "]";
		}else{
			return NULL_OBJ_PLACE_HOLDER;
		}
	}
	
	
}
