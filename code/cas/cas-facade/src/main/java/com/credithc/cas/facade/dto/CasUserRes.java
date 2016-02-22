package com.credithc.cas.facade.dto;

import com.credithc.common.facade.AbsRes;

public class CasUserRes extends AbsRes {

	private static final long serialVersionUID = 1L;

	private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
