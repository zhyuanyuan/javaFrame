package com.credithc.cache.common.api;

/**
 * 缓存包装器接口定义类。
 * 
 * @author sai.zhang
 */
public interface CacheWrapper {
	/**
	 * 是否缓存类。
	 * 
	 * @param clazz
	 * @return
	 */
	boolean isCacheClass(Class<?> clazz);

	/**
	 * 包装对象
	 * 
	 * @param object
	 * @return
	 */
	Object wrap(Object object);

	/**
	 * 客户端缓存对象
	 * 
	 * @param clazz
	 * @return
	 */
	<T> T clientCache(Class<T> clazz);
	
	/**
	 * 增加忽略缓存的类
	 * @param object
	 */
	void addIgnoreClass(Class<?> clazz);
}
