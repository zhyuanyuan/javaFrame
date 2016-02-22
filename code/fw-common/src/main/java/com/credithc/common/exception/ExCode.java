package com.credithc.common.exception;

public interface ExCode {
	
	public static final String BIZ_EX_PREFIX = "";
	public static final String SYS_EX_PREFIX = "-";
	
	public String getErrorCode();
	
	public String getErrorMsg();
}
