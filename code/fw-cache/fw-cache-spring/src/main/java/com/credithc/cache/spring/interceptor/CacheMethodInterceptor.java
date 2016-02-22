package com.credithc.cache.spring.interceptor;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.credithc.cache.impl.defau1t.description.CacheClass;
import com.credithc.cache.impl.defau1t.handler.CacheMethodInvocation;
import com.credithc.cache.impl.defau1t.handler.InvocationProcessor;
import com.credithc.cache.impl.defau1t.service.CacheService;

/**
 * Cache方法拦截器类。
 * 
 * @author sai.zhang
 */
public class CacheMethodInterceptor implements MethodInterceptor {
	protected Log logger = LogFactory.getLog(getClass());

	private Map<String, CacheClass> cacheClasses = new ConcurrentHashMap<String, CacheClass>();

	/**
	 * 调用处理器
	 */
	private InvocationProcessor invocProcessor;
	
	protected CacheClass getCacheClass(Class<?> clazz) {
		CacheClass cacheClass = cacheClasses.get(clazz.getName());
		if (cacheClass == null) {
			cacheClass = new CacheClass(clazz);
			cacheClasses.put(clazz.getName(), cacheClass);
		}

		return cacheClass;
	}
	
	private class MyCacheMethodInvocation implements CacheMethodInvocation {
		private MethodInvocation invoc;
		
		private CacheClass cacheClass;
		
		public Method getMethod() {
			return invoc.getMethod();
		}

		public Object getTargetObject() {
			return invoc.getThis();
		}

		public Object[] getArgs() {
			return invoc.getArguments();
		}

		public CacheClass getCacheClass() {
			return cacheClass;
		}

		public Object proceed() throws Throwable {
			return invoc.proceed();
		}
		
	}

	public Object invoke(MethodInvocation invoc) throws Throwable {
		final Method method = invoc.getMethod();
		CacheClass cacheClass = getCacheClass(method.getDeclaringClass());

		MyCacheMethodInvocation cInvoc = new MyCacheMethodInvocation();
		cInvoc.cacheClass = cacheClass;
		cInvoc.invoc = invoc;
		
		return invocProcessor.invoke(cInvoc);
	}

	public void setCacheService(CacheService cacheService) {
		this.invocProcessor = new InvocationProcessor(cacheService);
	}

}
