package com.credithc.cache.impl.redis.description;

/**
 * Redis实现的对象持有类。
 * 
 * @author sai.zhang
 */
public class RedisObjectHolder {
	/**
	 * 对象版本
	 */
	private String v;
	
	/**
	 * 类名称
	 */
	private String c;
	
	/**
	 * 对象JsonData
	 */
	private String d;
	
	/**
	 * 对象checkvalue
	 */
	private String ck;

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getD() {
		return d;
	}

	public void setD(String d) {
		this.d = d;
	}

	public String getC() {
		return c;
	}

	public void setC(String c) {
		this.c = c;
	}

	public String getCk() {
		return ck;
	}

	public void setCk(String ck) {
		this.ck = ck;
	}
}
