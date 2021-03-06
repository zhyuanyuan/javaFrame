package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfPfComponentEventDO extends AbsEntity{
	
	private String id;
	private String componentId;
	private String content;
	private String name;

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
	
}

