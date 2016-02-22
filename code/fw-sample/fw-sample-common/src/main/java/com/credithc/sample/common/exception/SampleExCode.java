package com.credithc.sample.common.exception;

import com.credithc.common.exception.ExCode;

public enum SampleExCode implements ExCode{
	
	SUCCESS("000000", "成功"),
	
	BIZ_SYS_ID_CANT_BE_NULL(BIZ_EX_PREFIX + "000001", "系统编号不能为空"),
	BIZ_SYS_SEQ_CANT_BE_NULL(BIZ_EX_PREFIX + "000002", "请求序列号不能为空"),
	//BIZ_...
	
	
	SYS_LOCK_TOMEOUT(SYS_EX_PREFIX + "000001", "锁超时"),
	//SYS_...
	SYS_UNKNOWN_ERROR(SYS_EX_PREFIX + "999999", "未知系统异常")
	;
    
    public String errorCode;
    public String errorMsg;
    
    private SampleExCode(String errorCode, String errorMsg){
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

	@Override
	public String getErrorCode() {
		return this.errorCode;
	}

	@Override
	public String getErrorMsg() {
		return this.errorMsg;
	}
}
