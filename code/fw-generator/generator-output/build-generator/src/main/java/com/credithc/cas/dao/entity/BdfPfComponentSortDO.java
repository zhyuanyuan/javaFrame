package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfPfComponentSortDO extends AbsEntity{
	
	private String id;
	private String controlId;
	private Integer order;
	private String parentComponentId;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getControlId() {
		return this.controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	
	public Integer getOrder() {
		return this.order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public String getParentComponentId() {
		return this.parentComponentId;
	}
	public void setParentComponentId(String parentComponentId) {
		this.parentComponentId = parentComponentId;
	}
	
}

