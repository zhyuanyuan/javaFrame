package com.credithc.cas.common.configs;

/**
 * cas常用配置
 * 
 * @author yangyang151020
 *
 */
public class CasXDiamondConfigs {

	private String hostName;// host.name=cas.com
	private String serverName;// server.name=http://a.com:8080
	private String serverPrefix;// server.prefix=http://a.com:8080/cas
	private String viewBasename;// cas.viewResolver.basename=custom_views
	private String defaultThemeName;// cas.themeResolver.defaultThemeName=cas-theme-default
	private String allowedSubnet;// cas.securityContext.status.allowedSubnet=127.0.0.1
	private boolean logoutRedirects;// cas.logout.followServiceRedirects=true

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerPrefix() {
		return serverPrefix;
	}

	public void setServerPrefix(String serverPrefix) {
		this.serverPrefix = serverPrefix;
	}

	public String getViewBasename() {
		return viewBasename;
	}

	public void setViewBasename(String viewBasename) {
		this.viewBasename = viewBasename;
	}

	public String getDefaultThemeName() {
		return defaultThemeName;
	}

	public void setDefaultThemeName(String defaultThemeName) {
		this.defaultThemeName = defaultThemeName;
	}

	public String getAllowedSubnet() {
		return allowedSubnet;
	}

	public void setAllowedSubnet(String allowedSubnet) {
		this.allowedSubnet = allowedSubnet;
	}

	public boolean isLogoutRedirects() {
		return logoutRedirects;
	}

	public void setLogoutRedirects(boolean logoutRedirects) {
		this.logoutRedirects = logoutRedirects;
	}

}
