package com.credithc.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.common.context.Context;
import com.credithc.common.log.LogableDTO;
import com.credithc.common.log.LoggerHelper;

public class FacadeOutAdvice {
	private static Logger logger = LoggerFactory.getLogger(FacadeOutAdvice.class);
	
	
	public Object facade(ProceedingJoinPoint jp) throws Throwable {
		Object rtnValue = null;
		try{
			LogableDTO logDto = null;
			Object [] args = jp.getArgs();
			if(args != null && args.length > 0){
				for(Object arg : args){
					if(arg instanceof LogableDTO){
						logDto = (LogableDTO)arg;
						logDto.setRequestChain((String)Context.get(LoggerHelper.REQUEST_CHAIN_CTX_KEY));
						break;
					}
				}
			}
			rtnValue = jp.proceed();
		}catch(Exception e){
			logger.error("facade out aop error", e);
			throw e;
		}finally{
			
		}
		return rtnValue;
	}

	
}
