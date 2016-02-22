package com.credithc.cache.impl.defau1t;

import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

import com.credithc.cache.common.api.CacheWrapper;
import com.credithc.cache.impl.defau1t.description.CacheClass;
import com.credithc.cache.impl.defau1t.handler.CacheWrappedInvocationHandler;
import com.credithc.cache.impl.defau1t.handler.ClientCacheInvocationHandler;
import com.credithc.cache.impl.defau1t.service.CacheService;

/**
 * 缺省的客户端对象工厂类。
 * 
 * @author sai.zhang
 */
public class DefaultCacheWrapper implements CacheWrapper {
	/**
	 * 缓存服务
	 */
	private CacheService cacheService;

	/**
	 * 忽略类
	 */
	private Set<String> ignoredClasses = new HashSet<String>();

	protected Object wrap(Object object, Class<?> targetClass) {
		CacheWrappedInvocationHandler invHandler = new CacheWrappedInvocationHandler(cacheService, object,
				object.getClass());
		ClassLoader cl = object.getClass().getClassLoader();
		Object clientObj = Proxy.newProxyInstance(cl, invHandler.getCacheClass().getInterfaces(), invHandler);

		return clientObj;
	}

	public Object wrap(Object object) {
		return wrap(object, object.getClass());
	}

	public boolean isCacheClass(Class<?> clazz) {
		if (ignoredClasses.contains(clazz.getName())) {
			return false;
		}

		return CacheClass.isCacheClass(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T> T clientCache(Class<T> clazz) {
		ClientCacheInvocationHandler invHandler = new ClientCacheInvocationHandler(cacheService, clazz);
		ClassLoader cl = clazz.getClassLoader();
		return (T) Proxy.newProxyInstance(cl, invHandler.getCacheClass().getInterfaces(), invHandler);
	}

	public void addIgnoreClass(Class<?> clazz) {
		ignoredClasses.add(clazz.getName());
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

}
