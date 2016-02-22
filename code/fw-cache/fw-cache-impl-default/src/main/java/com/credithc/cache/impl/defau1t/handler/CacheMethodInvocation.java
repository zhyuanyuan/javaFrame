package com.credithc.cache.impl.defau1t.handler;

import java.lang.reflect.Method;

import com.credithc.cache.impl.defau1t.description.CacheClass;

/**
 * 缓存方法调用类。
 * 
 * @author sai.zhang
 */
public interface CacheMethodInvocation {
	/**
	 * 获得方法
	 * @return
	 */
	Method getMethod();
	
	/**
	 * 获得目标对象
	 * @return
	 */
	Object getTargetObject();
	
	/**
	 * 获得参数
	 * @return
	 */
	Object[] getArgs();
	
	/**
	 * 获得缓存类描述
	 * @return
	 */
	CacheClass getCacheClass();
	
	/**
	 * 方法调用处理
	 * @return
	 */
	Object proceed() throws Throwable;
}
