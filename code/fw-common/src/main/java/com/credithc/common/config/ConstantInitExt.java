package com.credithc.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.credithc.common.constant.SysInfo;

@Configuration
public class ConstantInitExt extends BaseConfigExt{
	
	@Value("${app_name}")
	private String app_name;
	
	@Bean(name="sysInfo")
	public SysInfo createSysInfoBean() {
		SysInfo.currentSystemId = app_name;
		return new SysInfo();
	}
}
