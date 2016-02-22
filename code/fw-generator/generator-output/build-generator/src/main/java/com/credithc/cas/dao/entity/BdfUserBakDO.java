package com.credithc.cas.dao.entity;

import java.util.Date;
import java.sql.Timestamp

import com.credithc.common.dao.AbsEntity;

public class BdfUserBakDO extends AbsEntity{
	
	private String username;
	private String address;
	private Integer administrator;
	private Date birthday;
	private String cname;
	private String companyId;
	private Date createDate;
	private String email;
	private Integer enabled;
	private String ename;
	private Integer male;
	private String mobile;
	private String password;
	private String salt;

	public String getUsername() {
		return this.username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Integer getAdministrator() {
		return this.administrator;
	}
	public void setAdministrator(Integer administrator) {
		this.administrator = administrator;
	}
	
	public Date getBirthday() {
		return this.birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	
	public String getCname() {
		return this.cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	
	public String getCompanyId() {
		return this.companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public Integer getEnabled() {
		return this.enabled;
	}
	public void setEnabled(Integer enabled) {
		this.enabled = enabled;
	}
	
	public String getEname() {
		return this.ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	
	public Integer getMale() {
		return this.male;
	}
	public void setMale(Integer male) {
		this.male = male;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getSalt() {
		return this.salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
}

