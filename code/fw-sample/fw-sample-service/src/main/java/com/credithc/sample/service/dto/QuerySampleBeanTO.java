package com.credithc.sample.service.dto;

import java.io.Serializable;
import java.util.Date;

public class QuerySampleBeanTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String code;
	
	private String name;
	
	private String memo;
	
	private Date crtTime;
	
	private Date mdfTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Date getCrtTime() {
		return crtTime;
	}

	public void setCrtTime(Date crtTime) {
		this.crtTime = crtTime;
	}

	public Date getMdfTime() {
		return mdfTime;
	}

	public void setMdfTime(Date mdfTime) {
		this.mdfTime = mdfTime;
	}
}
