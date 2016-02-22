package com.credithc.cache.impl.defau1t.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.TargetClassAware;

import com.credithc.cache.impl.defau1t.description.CacheClass;
import com.credithc.cache.impl.defau1t.description.CacheMethod;
import com.credithc.cache.impl.defau1t.service.CacheService;

/**
 * 缓存包围对象调用处理句柄类。
 * 
 * @author sai.zhang
 */
public class CacheWrappedInvocationHandler implements InvocationHandler {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 缓存类
	 */
	private CacheClass cacheClass;

	/**
	 * 调用处理器
	 */
	private InvocationProcessor invocProcessor;

	/**
	 * 围绕对象
	 */
	private Object wrappedObject;

	/**
	 * 目标类
	 */
	private Class<?> targetClass;

	public class MyCacheMethodInvocation implements CacheMethodInvocation {
		private Method method;
		
		private Object targetObject;
		
		private Object[] args;
		
		private CacheClass cacheClass;
		
		public Method getMethod() {
			return method;
		}

		public Object getTargetObject() {
			return targetObject;
		}

		public Object[] getArgs() {
			return args;
		}

		public CacheClass getCacheClass() {
			return cacheClass;
		}

		public Object proceed() throws Throwable {
			try {
				return method.invoke(targetObject, args);
			} catch (IllegalArgumentException e) {
				throw e;
			} catch (IllegalAccessException e) {
				throw e;
			} catch (InvocationTargetException e) {
				throw e.getTargetException();
			}
		}
		
	}
	
	public CacheWrappedInvocationHandler(CacheService cacheService, Object wrappedObject, Class<?> targetClass) {
		this.invocProcessor = new InvocationProcessor(cacheService);
		this.cacheClass = new CacheClass(wrappedObject.getClass());
		this.wrappedObject = wrappedObject;
		this.targetClass = targetClass;
		
		Set<String> cacheEntities = new HashSet<String>();
		for ( CacheMethod cacheMethod : cacheClass.getCacheMethods().values() ) {
			String cacheEntity = cacheMethod.getDescription().getCacheEntity();
			if ( cacheEntity == null || cacheEntities.contains(cacheEntity) ) {
				continue;
			}
			
			// 重启，驱逐所有缓存对象，防止对象类更新导致的缓存错误。
			if ( logger.isDebugEnabled() ) {
				logger.debug("Evict all objects under cacheEntity=[" + cacheEntity + "].");
			}
			
			cacheEntities.add(cacheEntity);
			cacheService.evictObjects(cacheEntity);
		}
	}

	public Object invokeImpl(Object proxy, Method method, final Object[] oArgs) throws Throwable {
		if (method.getDeclaringClass().equals(TargetClassAware.class)) {
			return targetClass;
		}

		MyCacheMethodInvocation methodInvoc = new MyCacheMethodInvocation();
		methodInvoc.method = method;
		methodInvoc.args = oArgs;
		methodInvoc.cacheClass = cacheClass;
		methodInvoc.targetObject = wrappedObject;
		
		return invocProcessor.invoke(methodInvoc);
	}

	public Object invoke(Object proxy, Method method, Object[] oArgs) throws Throwable {
		try {
			return invokeImpl(proxy, method, oArgs);
		} catch (InvocationTargetException e) {
			throw e.getCause();
		}
	}

	public CacheClass getCacheClass() {
		return cacheClass;
	}

}
