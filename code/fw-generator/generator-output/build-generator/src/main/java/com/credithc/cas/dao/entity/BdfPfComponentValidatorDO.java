package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfPfComponentValidatorDO extends AbsEntity{
	
	private String id;
	private String componentId;
	private String validatorId;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getComponentId() {
		return this.componentId;
	}
	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}
	
	public String getValidatorId() {
		return this.validatorId;
	}
	public void setValidatorId(String validatorId) {
		this.validatorId = validatorId;
	}
	
}

