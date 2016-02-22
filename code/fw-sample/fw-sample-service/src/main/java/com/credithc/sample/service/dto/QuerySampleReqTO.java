package com.credithc.sample.service.dto;

import com.credithc.common.service.AbsReqTO;

public class QuerySampleReqTO extends AbsReqTO {

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
