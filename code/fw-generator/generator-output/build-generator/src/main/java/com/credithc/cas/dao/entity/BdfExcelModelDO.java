package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfExcelModelDO extends AbsEntity{
	
	private String id;
	private String comment;
	private String companyid;
	private Date createDate;
	private String datasourceName;
	private String dbType;
	private Integer endColumn;
	private Integer endRow;
	private String excelSheetName;
	private String helpDoc;
	private String name;
	private String primarykey;
	private String primarykeyType;
	private String processor;
	private String sequenceName;
	private Integer startColumn;
	private Integer startRow;
	private String tableLabel;
	private String tabelName;

	public String getId() {
		return this.id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public String getComment() {
		return this.comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public String getCompanyid() {
		return this.companyid;
	}
	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getDatasourceName() {
		return this.datasourceName;
	}
	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}
	
	public String getDbType() {
		return this.dbType;
	}
	public void setDbType(String dbType) {
		this.dbType = dbType;
	}
	
	public Integer getEndColumn() {
		return this.endColumn;
	}
	public void setEndColumn(Integer endColumn) {
		this.endColumn = endColumn;
	}
	
	public Integer getEndRow() {
		return this.endRow;
	}
	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}
	
	public String getExcelSheetName() {
		return this.excelSheetName;
	}
	public void setExcelSheetName(String excelSheetName) {
		this.excelSheetName = excelSheetName;
	}
	
	public String getHelpDoc() {
		return this.helpDoc;
	}
	public void setHelpDoc(String helpDoc) {
		this.helpDoc = helpDoc;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrimarykey() {
		return this.primarykey;
	}
	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}
	
	public String getPrimarykeyType() {
		return this.primarykeyType;
	}
	public void setPrimarykeyType(String primarykeyType) {
		this.primarykeyType = primarykeyType;
	}
	
	public String getProcessor() {
		return this.processor;
	}
	public void setProcessor(String processor) {
		this.processor = processor;
	}
	
	public String getSequenceName() {
		return this.sequenceName;
	}
	public void setSequenceName(String sequenceName) {
		this.sequenceName = sequenceName;
	}
	
	public Integer getStartColumn() {
		return this.startColumn;
	}
	public void setStartColumn(Integer startColumn) {
		this.startColumn = startColumn;
	}
	
	public Integer getStartRow() {
		return this.startRow;
	}
	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}
	
	public String getTableLabel() {
		return this.tableLabel;
	}
	public void setTableLabel(String tableLabel) {
		this.tableLabel = tableLabel;
	}
	
	public String getTabelName() {
		return this.tabelName;
	}
	public void setTabelName(String tabelName) {
		this.tabelName = tabelName;
	}
	
}

