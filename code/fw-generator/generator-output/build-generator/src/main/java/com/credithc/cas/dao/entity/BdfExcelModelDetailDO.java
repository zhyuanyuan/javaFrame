package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfExcelModelDetailDO extends AbsEntity{
	
	private String id;
	private Integer excelColumn;
	private String excelModelId;
	private String interceptor;
	private String name;
	private String tableColumn;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getExcelColumn() {
		return this.excelColumn;
	}
	public void setExcelColumn(Integer excelColumn) {
		this.excelColumn = excelColumn;
	}
	
	public String getExcelModelId() {
		return this.excelModelId;
	}
	public void setExcelModelId(String excelModelId) {
		this.excelModelId = excelModelId;
	}
	
	public String getInterceptor() {
		return this.interceptor;
	}
	public void setInterceptor(String interceptor) {
		this.interceptor = interceptor;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getTableColumn() {
		return this.tableColumn;
	}
	public void setTableColumn(String tableColumn) {
		this.tableColumn = tableColumn;
	}
	
}

