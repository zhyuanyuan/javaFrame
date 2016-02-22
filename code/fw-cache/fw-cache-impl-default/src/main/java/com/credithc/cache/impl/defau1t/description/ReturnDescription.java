package com.credithc.cache.impl.defau1t.description;

import java.util.ArrayList;
import java.util.List;

/**
 * 返回描述类。
 * 
 * @author sai.zhang
 */
public class ReturnDescription {
	public static class KeyItem {
		/**
		 * 属性名称
		 */
		private String propertyName;
		
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

		public String getJxpathAccessor() {
			return jxpathAccessor;
		}

		public void setJxpathAccessor(String jxpathAccessor) {
			this.jxpathAccessor = jxpathAccessor;
		}
	}
	
	/**
	 * 实体编号
	 */
	private String entity;
	
	/**
	 * 生存周期（秒）
	 */
	private int ttl;
	
	/**
	 * 键值表
	 */
	private List<KeyItem> keys = new ArrayList<KeyItem>();

	public String getEntity() {
		return entity;
	}

	public void setEntity(String entity) {
		this.entity = entity;
	}

	public List<KeyItem> getKeys() {
		return keys;
	}

	public void setKeys(List<KeyItem> keys) {
		this.keys = keys;
	}

	public int getTtl() {
		return ttl;
	}

	public void setTtl(int ttl) {
		this.ttl = ttl;
	}
	
}
