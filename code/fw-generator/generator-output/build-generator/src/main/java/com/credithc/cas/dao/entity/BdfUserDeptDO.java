package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfUserDeptDO extends AbsEntity{
	
	private String id;
	private String deptId;
	private String username;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDeptId() {
		return this.deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	
	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
}

