package com.credithc.sample.service.dto;

import com.credithc.common.service.AbsReqTO;

public class DeleteSampleReqTO extends AbsReqTO {

	private static final long serialVersionUID = 1L;

	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
