package com.credithc.common.facade;

public abstract class AbsRes extends AbsDTO {
    
    private static final long serialVersionUID = 1L;

    protected String respCode;
    
    protected String respMsg;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
    
}
