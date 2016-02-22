package com.credithc.cache.impl.defau1t.description;

import java.lang.reflect.Method;

/**
 * 缓存方法类。
 * 
 * @author sai.zhang
 */
public class CacheMethod {
	/**
	 * 类
	 */
	private Class<?> clazz;
	
	/**
	 * 方法
	 */
	private Method method;
	
	/**
	 * 缓存方法描述
	 */
	private CacheMethodDescription description;

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public CacheMethodDescription getDescription() {
		return description;
	}

	public void setDescription(CacheMethodDescription description) {
		this.description = description;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public void setClazz(Class<?> clazz) {
		this.clazz = clazz;
	}
}
