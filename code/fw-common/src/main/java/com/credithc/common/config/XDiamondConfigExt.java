package com.credithc.common.config;
import io.github.xdiamond.client.spring.XDiamondConfigFactoryBean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class XDiamondConfigExt extends BaseConfigExt{
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Bean(name="xDiamondConfig")
	public XDiamondConfigFactoryBean xDiamondConfig() {
		if(super.isEnabled("xdiamond_enabled")){
			String groupId = LocalProperties.getValue("xdiamond_group_id");
			String artifactId = LocalProperties.getValue("xdiamond_artifact_id");
			String version = LocalProperties.getValue("xdiamond_version");
			
			logger.info("xdiamond_server_host : " + LocalProperties.getSystemConfig("xdiamond_server_host"));
			logger.info("xdiamond_server_port : " + LocalProperties.getSystemConfig("xdiamond_server_port"));
			logger.info("xdiamond_profile : " + LocalProperties.getSystemConfig("xdiamond_profile"));
			logger.info("xdiamond_group_id :" + groupId);
			logger.info("xdiamond_artifact_id :" + artifactId);
			logger.info("xdiamond_version :" + version);
			
			XDiamondConfigFactoryBean xdBean = new XDiamondConfigFactoryBean();
			
			xdBean.setServerHost(LocalProperties.getSystemConfig("xdiamond_server_host"));    //10.100.1.73
			xdBean.setServerPort(LocalProperties.getSystemConfig("xdiamond_server_port"));    //5678
			
			xdBean.setProfile(LocalProperties.getSystemConfig("xdiamond_profile"));    //product
			xdBean.setSecretKey(LocalProperties.getSystemConfig("xdiamond_secret_key"));    //6upamM0A4wHAVJdP
			
			xdBean.setGroupId(groupId);    //com.credithc
			xdBean.setArtifactId(artifactId);    //sample
			xdBean.setVersion(version);    //1.0.0
			
			return xdBean;
		}else{
			return null;
		}
	}
	
	
}
