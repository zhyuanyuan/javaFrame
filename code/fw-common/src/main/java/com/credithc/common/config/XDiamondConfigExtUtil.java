package com.credithc.common.config;

import io.github.xdiamond.client.XDiamondConfig;
import io.github.xdiamond.client.spring.XDiamondConfigFactoryBean;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XDiamondConfigExtUtil {
	private static final Logger logger = LoggerFactory.getLogger(XDiamondConfigExtUtil.class);
	
	private static Properties properties;
	
	static{
		try {
			XDiamondConfigFactoryBean xDiamondBean = new XDiamondConfigExt().xDiamondConfig();
			xDiamondBean.afterPropertiesSet();
			XDiamondConfig config = (XDiamondConfig)xDiamondBean.getObject();
			properties = config.getProperties();
		} catch (Exception e) {
			logger.error("Init XDiamondConfigExtUtil error", e);
		}
	}
	
	public static String getProperty(String key){
		return properties.getProperty(key);
	}

}
