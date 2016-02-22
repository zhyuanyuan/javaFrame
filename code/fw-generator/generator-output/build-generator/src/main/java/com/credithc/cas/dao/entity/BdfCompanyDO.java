package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfCompanyDO extends AbsEntity{
	
	private String id;
	private String desc;
	private String name;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getDesc() {
		return this.desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

