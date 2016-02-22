package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfRoleResourceDO extends AbsEntity{
	
	private String id;
	private String packageId;
	private String roleId;
	private String urlId;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getPackageId() {
		return this.packageId;
	}
	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}
	
	public String getRoleId() {
		return this.roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	public String getUrlId() {
		return this.urlId;
	}
	public void setUrlId(String urlId) {
		this.urlId = urlId;
	}
	
}

