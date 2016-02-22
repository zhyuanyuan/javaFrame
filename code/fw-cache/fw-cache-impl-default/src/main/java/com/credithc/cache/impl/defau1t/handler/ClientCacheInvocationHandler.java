package com.credithc.cache.impl.defau1t.handler;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.cache.common.api.NotFoundCachedException;
import com.credithc.cache.impl.defau1t.description.CacheClass;
import com.credithc.cache.impl.defau1t.description.CacheMethod;
import com.credithc.cache.impl.defau1t.description.CacheMethodDescription;
import com.credithc.cache.impl.defau1t.service.CacheService;

/**
 * 客户端缓存对象调用处理句柄类。
 * 
 * @author sai.zhang
 */
public class ClientCacheInvocationHandler implements InvocationHandler {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 缓存类
	 */
	private CacheClass cacheClass;

	/**
	 * 缓存服务
	 */
	private CacheService cacheService;

	public ClientCacheInvocationHandler(CacheService cacheService, Class<?> clazz) {
		this.cacheClass = new CacheClass(clazz);
		this.cacheService = cacheService;
	}

	public Object invokeImpl(Object proxy, Method method, Object[] oArgs) throws Throwable {
		CacheMethod cacheMethod = cacheClass.getCacheMethod(method);

		if (cacheMethod != null) {
			CacheMethodDescription methodDesc = cacheMethod.getDescription();

			if (methodDesc.isCacheFlag()) {
				// 访问缓存对象
				Object retObj = cacheService.getCachedObject(cacheMethod, oArgs);
				if (logger.isDebugEnabled()) {
					logger.debug("Hit cache, class=[" + method.getDeclaringClass().getName() + "], method=["
							+ cacheMethod.getMethod().getName() + "].");
				}

				return retObj;
			}
		}

		throw new NotFoundCachedException();
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

	public CacheService getCacheService() {
		return cacheService;
	}

}
