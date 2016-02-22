package com.credithc.cas.service.dto;

import com.credithc.common.service.AbsResTO;

public class CasUserResTO extends AbsResTO {

	private static final long serialVersionUID = 1L;

	private boolean success;
	private String code;
	private String msg;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "{\"success\":" + success + ", \"code\":\"" + code
				+ "\", \"msg\":\"" + msg + "\"}";
	}

}

