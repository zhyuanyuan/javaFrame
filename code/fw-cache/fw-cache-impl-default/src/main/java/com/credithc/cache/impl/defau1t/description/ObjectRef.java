package com.credithc.cache.impl.defau1t.description;

/**
 * 对象引用类。
 * 
 * @author sai.zhang
 */
public class ObjectRef {
	/**
	 * 实体名称
	 */
	private String entity;
	
	/**
	 * 键值
	 */
	private String key;
	
	/**
	 * 对象版本
	 */
	private String objectVersion;

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getObjectVersion() {
		return objectVersion;
	}

	public void setObjectVersion(String objectVersion) {
		this.objectVersion = objectVersion;
	}
	
}
