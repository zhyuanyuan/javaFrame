package com.credithc.common.log;

import com.credithc.common.constant.SysInfo;


public abstract class LogableDTO {
	
	private String sysId;
	    
	private String sysSeq;
	 
	private String businessId;
	
	private String requestChain = "";
	
	public LogableDTO(){
		setSysId(SysInfo.currentSystemId);
	}
	
	public String getSysId() {
		return sysId;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public String getSysSeq() {
		return sysSeq;
	}

	public void setSysSeq(String sysSeq) {
		this.sysSeq = sysSeq;
	}

	public String getBusinessId() {
		return businessId;
	}

	public void setBusinessId(String businessId) {
		this.businessId = businessId;
	}

	public String getRequestChain() {
		return requestChain;
	}

	public void setRequestChain(String requestChain) {
		this.requestChain = requestChain;
	}
	 
}
