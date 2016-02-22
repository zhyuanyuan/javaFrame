package com.credithc.cas.service.dto;

import com.credithc.common.service.AbsReqTO;

public class UpdatePasswordReqTO extends AbsReqTO {

	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	private String newPassword;

	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
}

