package com.credithc.cache.impl.defau1t.description;

/**
 * 缓存方法描述类。
 * 
 * @author sai.zhang
 */
public class CacheMethodDescription {
	/**
	 * 缓存标志
	 */
	private boolean cacheFlag;
	
	/**
	 * 缓存生存周期（秒）
	 */
	private int cacheTtl;
	
	/**
	 * 缓存实体名称
	 */
	private String cacheEntity;
	
	/**
	 * 缓存实体检查标签，用于判断版本一致性
	 */
	private String cahceEntityChecksum;
	
	/**
	 * 缓存null标志
	 */
	private boolean cacheNull;
	
	/**
	 * 缓存键值
	 */
	private CacheKeyDescription[] cacheKeyDescs;
	
	/**
	 * 返回描述
	 */
	private ReturnDescription returnDesc;
	
	/**
	 * 缓存驱逐描述
	 */
	private CacheEvictionDescription cacheEvictionDesc;

	public int getCacheTtl() {
		return cacheTtl;
	}

	public void setCacheTtl(int cacheTtl) {
		this.cacheTtl = cacheTtl;
	}

	public String getCacheEntity() {
		return cacheEntity;
	}

	public void setCacheEntity(String cacheEntity) {
		this.cacheEntity = cacheEntity;
	}

	public boolean isCacheNull() {
		return cacheNull;
	}

	public void setCacheNull(boolean cacheNull) {
		this.cacheNull = cacheNull;
	}

	public CacheKeyDescription[] getCacheKeyDescs() {
		return cacheKeyDescs;
	}

	public void setCacheKeyDescs(CacheKeyDescription[] cacheKeyDescs) {
		this.cacheKeyDescs = cacheKeyDescs;
	}

	public ReturnDescription getReturnDesc() {
		return returnDesc;
	}

	public void setReturnDesc(ReturnDescription returnDesc) {
		this.returnDesc = returnDesc;
	}

	public CacheEvictionDescription getCacheEvictionDesc() {
		return cacheEvictionDesc;
	}

	public void setCacheEvictionDesc(CacheEvictionDescription cacheEvictionDesc) {
		this.cacheEvictionDesc = cacheEvictionDesc;
	}

	public boolean isCacheFlag() {
		return cacheFlag;
	}

	public void setCacheFlag(boolean cacheFlag) {
		this.cacheFlag = cacheFlag;
	}

	public String getCahceEntityChecksum() {
		return cahceEntityChecksum;
	}

	public void setCahceEntityChecksum(String cahceEntityChecksum) {
		this.cahceEntityChecksum = cahceEntityChecksum;
	}

}
