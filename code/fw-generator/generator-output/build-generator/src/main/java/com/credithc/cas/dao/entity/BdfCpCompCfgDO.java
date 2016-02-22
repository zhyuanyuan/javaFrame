package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfCpCompCfgDO extends AbsEntity{
	
	private String id;
	private String cols;
	private String controlId;
	private String hideMode;
	private String meta1;
	private String meta2;
	private String meta3;
	private String name;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCols() {
		return this.cols;
	}
	public void setCols(String cols) {
		this.cols = cols;
	}
	
	public String getControlId() {
		return this.controlId;
	}
	public void setControlId(String controlId) {
		this.controlId = controlId;
	}
	
	public String getHideMode() {
		return this.hideMode;
	}
	public void setHideMode(String hideMode) {
		this.hideMode = hideMode;
	}
	
	public String getMeta1() {
		return this.meta1;
	}
	public void setMeta1(String meta1) {
		this.meta1 = meta1;
	}
	
	public String getMeta2() {
		return this.meta2;
	}
	public void setMeta2(String meta2) {
		this.meta2 = meta2;
	}
	
	public String getMeta3() {
		return this.meta3;
	}
	public void setMeta3(String meta3) {
		this.meta3 = meta3;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}

