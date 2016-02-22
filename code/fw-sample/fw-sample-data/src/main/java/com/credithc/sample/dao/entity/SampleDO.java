package com.credithc.sample.dao.entity;

import java.util.Date;

import com.credithc.common.annotation.FW_TABLE;
import com.credithc.common.dao.AbsEntity;


@FW_TABLE(value="t_sample")
public class SampleDO extends AbsEntity{
	
	private static final long serialVersionUID = 1L;

	private String id;
	
	private String code;
	
	private String name;
	
	private String memo;
	
	private Date crt_time;
	
	private Date mdf_time;

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

	public Date getMdf_time() {
		return mdf_time;
	}

	public void setMdf_time(Date mdf_time) {
		this.mdf_time = mdf_time;
	}



}
