package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfPfValidatorPropertyDO extends AbsEntity{
	
	private String id;
	private String name;
	private String validatorId;
	private String value;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getValidatorId() {
		return this.validatorId;
	}
	public void setValidatorId(String validatorId) {
		this.validatorId = validatorId;
	}
	
	public String getValue() {
		return this.value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}

