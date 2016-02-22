package com.credithc.cache.impl.defau1t.service;

import com.credithc.cache.common.api.NotFoundCachedException;
import com.credithc.cache.impl.defau1t.description.CacheMethod;


/**
 * 缓存服务接口定义类。
 * 
 * @author sai.zhang
 */
public interface CacheService {
	/**
	 * 获得缓存对象
	 * @param method
	 * @param args
	 * @return
	 * @throws NotFoundCachedException
	 */
	Object getCachedObject(CacheMethod method, Object[] args) throws NotFoundCachedException;
	
	/**
	 * 缓存对象
	 * @param method
	 * @param args
	 * @param retObj
	 */
	void cachedObject(CacheMethod method, Object[] args, Object retObj);
	
	/**
	 * 驱逐对象
	 * @param method
	 * @param args
	 */
	void evictObjects(CacheMethod method, Object[] args);
	
	/**
	 * 驱逐对象（用于服务重启后清除所有缓存对象，防止对象类更新导致的不一致）
	 * @param cacheEntity
	 */
	void evictObjects(String cacheEntity);
}
