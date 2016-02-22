package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfCpCompMembDO extends AbsEntity{
	
	private String id;
	private String caption;
	private Integer colSpan;
	private String controlName;
	private String controlType;
	private Integer order;
	private String parentControlName;
	private Integer rowSpan;
	private Integer visible;
	private String width;
	private String configId;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getCaption() {
		return this.caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public Integer getColSpan() {
		return this.colSpan;
	}
	public void setColSpan(Integer colSpan) {
		this.colSpan = colSpan;
	}
	
	public String getControlName() {
		return this.controlName;
	}
	public void setControlName(String controlName) {
		this.controlName = controlName;
	}
	
	public String getControlType() {
		return this.controlType;
	}
	public void setControlType(String controlType) {
		this.controlType = controlType;
	}
	
	public Integer getOrder() {
		return this.order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	
	public String getParentControlName() {
		return this.parentControlName;
	}
	public void setParentControlName(String parentControlName) {
		this.parentControlName = parentControlName;
	}
	
	public Integer getRowSpan() {
		return this.rowSpan;
	}
	public void setRowSpan(Integer rowSpan) {
		this.rowSpan = rowSpan;
	}
	
	public Integer getVisible() {
		return this.visible;
	}
	public void setVisible(Integer visible) {
		this.visible = visible;
	}
	
	public String getWidth() {
		return this.width;
	}
	public void setWidth(String width) {
		this.width = width;
	}
	
	public String getConfigId() {
		return this.configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	
}

