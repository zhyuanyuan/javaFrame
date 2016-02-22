package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfPfComponentDO extends AbsEntity{
	
	private String id;
	private String assignTargetId;
	private String controlId;
	private String type;
	private String url;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getAssignTargetId() {
		return this.assignTargetId;
	}
	public void setAssignTargetId(String assignTargetId) {
		this.assignTargetId = assignTargetId;
	}
	
	public String getControlId() {
		return this.controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	
	public String getType() {
		return this.type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getUrl() {
		return this.url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
}

