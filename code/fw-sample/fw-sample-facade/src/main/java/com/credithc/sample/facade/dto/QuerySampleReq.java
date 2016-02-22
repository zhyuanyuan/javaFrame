package com.credithc.sample.facade.dto;

import com.credithc.common.facade.AbsReq;

public class QuerySampleReq extends AbsReq {

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String code;

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

}
