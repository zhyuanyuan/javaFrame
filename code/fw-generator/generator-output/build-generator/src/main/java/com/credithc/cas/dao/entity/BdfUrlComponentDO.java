package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfUrlComponentDO extends AbsEntity{
	
	private String id;
	private String authorityType;
	private String roleId;
	private String urlId;
	private String componentId;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAuthorityType() {
		return this.authorityType;
	}
	public void setAuthorityType(String authorityType) {
		this.authorityType = authorityType;
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
	
	public String getComponentId() {
		return this.componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	
}

