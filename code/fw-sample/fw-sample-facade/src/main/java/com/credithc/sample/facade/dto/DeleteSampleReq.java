package com.credithc.sample.facade.dto;

import com.credithc.common.facade.AbsReq;

public class DeleteSampleReq extends AbsReq {

	private static final long serialVersionUID = 1L;
	
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
