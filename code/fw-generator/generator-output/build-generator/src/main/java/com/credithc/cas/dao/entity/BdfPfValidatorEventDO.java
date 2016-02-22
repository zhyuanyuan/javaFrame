package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfPfValidatorEventDO extends AbsEntity{
	
	private String id;
	private String content;
	private String name;
	private String validatorId;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
}

