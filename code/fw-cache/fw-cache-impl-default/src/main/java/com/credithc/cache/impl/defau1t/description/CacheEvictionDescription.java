package com.credithc.cache.impl.defau1t.description;

import java.util.ArrayList;
import java.util.List;


/**
 * 缓存驱逐描述类。
 * 
 * @author sai.zhang
 */
public class CacheEvictionDescription {
	public static class CacheEvictItem {
		/**
		 * 实体名称
		 */
		private String entity;
		
		/**
		 * 缓存键值描述列表
		 */
		private CacheKeyDescription[] cacheKeyDesc;

		public String getEntity() {
			return entity;
		}

		public void setEntity(String entity) {
			this.entity = entity;
		}

		public CacheKeyDescription[] getCacheKeyDesc() {
			return cacheKeyDesc;
		}

		public void setCacheKeyDesc(CacheKeyDescription[] cacheKeyDesc) {
			this.cacheKeyDesc = cacheKeyDesc;
		}
	}
	
	private List<CacheEvictItem> cacheEvictItems = new ArrayList<CacheEvictItem>();

	public List<CacheEvictItem> getCacheEvictItems() {
		return cacheEvictItems;
	}

	public void setCacheEvictItems(List<CacheEvictItem> cacheEvictItems) {
		this.cacheEvictItems = cacheEvictItems;
	}

}
