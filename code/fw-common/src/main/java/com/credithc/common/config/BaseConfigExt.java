package com.credithc.common.config;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseConfigExt {
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected boolean isEnabled(String key){
		String enabled = LocalProperties.getValue(key);
		logger.info(key + "=" + enabled);
		if(StringUtils.isNotBlank(enabled)){
			if(StringUtils.equalsIgnoreCase(enabled, "true")){
				return true;
			}
		}
		return false;
	}

}
