package com.credithc.common.aop;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.common.constant.SysInfo;
import com.credithc.common.context.Context;
import com.credithc.common.log.LogableDTO;
import com.credithc.common.log.LoggerHelper;
import com.dianping.cat.Cat;
import com.dianping.cat.message.Transaction;

public class FacadeInAdvice {
	private static Logger logger = LoggerFactory.getLogger(FacadeInAdvice.class);
	
	private String catType;
	
	private String appName;
	
	public Object facade(ProceedingJoinPoint jp) throws Throwable {
		Object rtnValue = null;
		Transaction t = null;
		try{
			Context.initContext();
			
			String methodSignature = getMethodSignature(jp);			
			String catInfo = appendReqChain(jp);
			
			Cat.logMetricForCount(appName,  methodSignature);
			t = Cat.getProducer().newTransaction(catType, methodSignature);
			t.addData("request_info", catInfo);
			
			rtnValue = jp.proceed();
			
			t.setStatus(Transaction.SUCCESS);
		}catch(Exception e){
			t.setStatus(e);
			logger.error("facade in aop error", e);
			throw e;
		}finally{
			Context.destoryContext();
			
			if(t != null){
				t.complete();
			}
		}
		return rtnValue;
	}

	private String appendReqChain(ProceedingJoinPoint jp) {
		LogableDTO logDto = null;
		Object [] args = jp.getArgs();
		if(args != null && args.length > 0){
			for(Object arg : args){
				if(arg instanceof LogableDTO){
					logDto = (LogableDTO)arg;
					String reqChain = StringUtils.trimToEmpty(logDto.getRequestChain());
					if(!StringUtils.endsWith(reqChain, logDto.getSysId())){
						reqChain = reqChain + ">" + logDto.getSysId();
					}
					reqChain = reqChain + ">" + SysInfo.currentSystemId;
					
					logDto.setRequestChain(reqChain);
					Context.put(LoggerHelper.REQUEST_CHAIN_CTX_KEY, logDto.getRequestChain());
					break;
				}
			}
		}
		return logDto == null ? "" : LoggerHelper.getShortBizInfo(logDto);
	}

	private String getMethodSignature(ProceedingJoinPoint jp) {
		try {
			StringBuilder methodSignature = new StringBuilder(jp.getTarget().getClass().getSimpleName()).append(".").append(jp.getSignature().getName()).append("(");
			Object [] args = jp.getArgs();
			if(args != null && args.length > 0){
				for(int i=0; i<args.length; i++){
					Object arg = args[i];
					if(i > 0){
						methodSignature.append(",");
					}
					if(arg != null){
						methodSignature.append(arg.getClass().getSimpleName());
					}else{
						methodSignature.append("NULL");
					}
				}
			}
			methodSignature.append(")");
			return methodSignature.toString();
		} catch (Exception e) {
			return "GET_MTH_SIGNATURE_ERROR";
		}
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public void setCatType(String catType) {
		this.catType = catType;
	}
	
}
