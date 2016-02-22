package com.credithc.cas.common.utils;

import com.credithc.common.service.AbsResTO;

/**
 * 异步请求通用返回信息
 * @author yangyang151020
 * @date 2015-12-21
 *
 */
public class AsynResTO extends AbsResTO {
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
