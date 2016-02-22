package com.credithc.cas.facade.dto;

import java.util.Date;

import com.credithc.common.facade.AbsDTO;

public class CasUserDTO extends AbsDTO{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String userName;
	private String password;
	private Boolean isAdmin;
	private Boolean enabled;
	private String address;
	private Date birthday;
	private String cname;
	private String companyId;
	private String email;
	private String ename;
	private Boolean male;
	private String mobile;
	private String salt;
	private Date createDate;

	public Long getId() {
		return this.id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getUserName() {
		return this.userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		return this.password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getIsAdmin() {
		return this.isAdmin;
	}
	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
	public Boolean getEnabled() {
		return this.enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	
	public String getAddress() {
		return this.address;
	}
	public void setAddress(String address) {
		this.address = address;
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
	
	public String getEmail() {
		return this.email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEname() {
		return this.ename;
	}
	public void setEname(String ename) {
		this.ename = ename;
	}
	
	public Boolean getMale() {
		return this.male;
	}
	public void setMale(Boolean male) {
		this.male = male;
	}
	
	public String getMobile() {
		return this.mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public String getSalt() {
		return this.salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	
	public Date getCreateDate() {
		return this.createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Override
	public String toString() {
		return "CasUserDTO [id=" + id + ", userName=" + userName
				+ ", password=" + password + ", isAdmin=" + isAdmin
				+ ", enabled=" + enabled + ", address=" + address
				+ ", birthday=" + birthday + ", cname=" + cname
				+ ", companyId=" + companyId + ", email=" + email + ", ename="
				+ ename + ", male=" + male + ", mobile=" + mobile + ", salt="
				+ salt + ", createDate=" + createDate + "]";
	}
	
}
