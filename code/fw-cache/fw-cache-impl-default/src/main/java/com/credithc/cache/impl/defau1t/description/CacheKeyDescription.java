package com.credithc.cache.impl.defau1t.description;

/**
 * 缓存键值描述类
 * 
 * @author sai.zhang
 */
public class CacheKeyDescription {
	/**
	 * 值前缀
	 */
	private String propertyName;
	
	/**
	 * 参数编号
	 */
	private int argIdx;
	
	/**
	 * jxpath访问路径
	 */
	private String jxpathAccessor;

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public int getArgIdx() {
		return argIdx;
	}

	public void setArgIdx(int argIdx) {
		this.argIdx = argIdx;
	}

	public String getJxpathAccessor() {
		return jxpathAccessor;
	}

	public void setJxpathAccessor(String jxpathAccessor) {
		this.jxpathAccessor = jxpathAccessor;
	}
}
