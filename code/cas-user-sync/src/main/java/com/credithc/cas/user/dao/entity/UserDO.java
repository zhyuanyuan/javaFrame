package com.credithc.cas.user.dao.entity;

import com.credithc.common.dao.AbsEntity;

public class UserDO extends AbsEntity {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7155754099113169319L;

	private Long id;
	private String userName;
	private Boolean enabled;
	private String cname;
	private String companyId;
	private String ename;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getCname() {
		return cname;
	}

	public void setCname(String cname) {
		this.cname = cname;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

	public String getEname() {
		return ename;
	}

	public void setEname(String ename) {
		this.ename = ename;
	}

	@Override
	public String toString() {
		return "UserDO [id=" + id + ", userName=" + userName + ", enabled="
				+ enabled + ", cname=" + cname + ", companyId=" + companyId
				+ ", ename=" + ename + "]";
	}

}

