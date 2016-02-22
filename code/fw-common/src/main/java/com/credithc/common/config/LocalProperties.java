package com.credithc.common.config;

import java.util.Iterator;
import java.util.Properties;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.alibaba.dubbo.common.utils.StringUtils;

public class LocalProperties {
	private static Logger logger = LoggerFactory.getLogger(LocalProperties.class);
	
	private static Object propsLock = new Object();
	private static Properties props;
	
	public static String getValue(String key){
		initPropertiesIfNessary();
		return props.getProperty(key);
	}

	private static void initPropertiesIfNessary() {
		if(props == null){
			synchronized (propsLock) {
				if(props == null){
					props = new Properties();
					Resource[] resources = searchResources();   
					for(Resource r : resources){
						Configuration localCfg = readResource(r);
						if(localCfg != null){
							Iterator<String> keysItr = localCfg.getKeys();
							while(keysItr.hasNext()){
								String k = keysItr.next();
								String v = localCfg.getString(k);
								props.put(k, v);
							}
						}
					}
				}
			}
		}
	}

	private static Configuration readResource(Resource r)  {
		try {
			logger.info("load property file : " + r.getURL().getPath());
			Configuration localCfg = new PropertiesConfiguration(r.getURL());
			return localCfg;
		} catch (Exception e) {
			logger.error("", e);
		}
		return null;
	}

	private static Resource[] searchResources() {
		try {
			ResourcePatternResolver resolver = (ResourcePatternResolver) new PathMatchingResourcePatternResolver();  
			Resource[] resources = (Resource[]) resolver.getResources("classpath*:META-INF/*.properties");
			return resources;
		} catch (Exception e) {
			logger.error("", e);
		}
		return new Resource[]{};
	}
	
	public static String getSystemConfig(String key){
		if(StringUtils.isNotEmpty(key)){
			String value = System.getProperty(key);
			if(StringUtils.isBlank(value)){
				value = System.getenv(key);
			}
			return value;
		}
		return null;
	}

}
