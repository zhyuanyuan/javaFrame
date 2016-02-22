package com.credithc.cas.common.configs;

/**
 * 应用URL配置
 * @author yangyang151020
 *
 */
public class AppUrlXDiamondConfigs {
	
	private String userinfoUrl;// 用户信息URL
	private String updatePwdUrl;// 修改密码URL
	private String eamUrl;// 账户URL
	private String appUrl;// 个贷URL
	private String csmUr;// 催收URL

	public String getUserinfoUrl() {
		return userinfoUrl;
	}

	public void setUserinfoUrl(String userinfoUrl) {
		this.userinfoUrl = userinfoUrl;
	}

	public String getUpdatePwdUrl() {
		return updatePwdUrl;
	}

	public void setUpdatePwdUrl(String updatePwdUrl) {
		this.updatePwdUrl = updatePwdUrl;
	}

	public String getEamUrl() {
		return eamUrl;
	}

	public void setEamUrl(String eamUrl) {
		this.eamUrl = eamUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public String getCsmUr() {
		return csmUr;
	}

	public void setCsmUr(String csmUr) {
		this.csmUr = csmUr;
	}

}
