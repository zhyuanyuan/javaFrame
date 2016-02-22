package com.credithc.common.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

@Configuration
public class AppRefreshingListener implements ApplicationListener<ContextRefreshedEvent> {

	private static boolean logChecked = false;
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		checkLogBridegeToSlf4j(event);
	}
	private void checkLogBridegeToSlf4j(ContextRefreshedEvent event) {
		if(!event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")){
			if(!logChecked){
				synchronized (this) {
					if(logChecked){
						return;
					}
					logChecked = true;
				}
				
				//for check logging right
				org.slf4j.Logger slf4jLogger = org.slf4j.LoggerFactory.getLogger(this.getClass());
				org.apache.log4j.Logger log4jLogger = org.apache.log4j.Logger.getLogger(this.getClass());
				org.apache.commons.logging.Log commonsLogger = org.apache.commons.logging.LogFactory.getLog(this.getClass());
				java.util.logging.Logger jdkLogger = java.util.logging.Logger.getLogger(this.getClass().getName());
				//checking...all logs redirect to logback!!!
				slf4jLogger.info("slf4jLogger:print testing...");
				log4jLogger.info("log4jLogger:print testing...");
				commonsLogger.info("commonsLogger:print testing...");
				jdkLogger.info("jdkLogger:print testing...");
			}
		}
	}


}
