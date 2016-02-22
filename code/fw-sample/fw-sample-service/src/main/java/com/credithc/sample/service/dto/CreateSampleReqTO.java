package com.credithc.sample.service.dto;

import java.util.Date;

import com.credithc.common.service.AbsReqTO;

public class CreateSampleReqTO extends AbsReqTO {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String code;
	
	private String name;
	
	private String memo;
	
	private Date crt_time;
	
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

	public Date getCrt_time() {
		return crt_time;
	}

	public void setCrt_time(Date crt_time) {
		this.crt_time = crt_time;
	}

	public Date getMdfTime() {
		return mdfTime;
	}

	public void setMdfTime(Date mdfTime) {
		this.mdfTime = mdfTime;
	}

}
