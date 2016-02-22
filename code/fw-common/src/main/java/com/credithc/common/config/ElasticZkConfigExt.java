package com.credithc.common.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dangdang.ddframe.reg.base.CoordinatorRegistryCenter;
import com.dangdang.ddframe.reg.zookeeper.ZookeeperConfiguration;
import com.dangdang.ddframe.reg.zookeeper.ZookeeperRegistryCenter;

@Configuration
public class ElasticZkConfigExt extends BaseConfigExt{
	
	@Bean(name="jobRegCenter")
	public CoordinatorRegistryCenter jobRegCenter() {
		if(super.isEnabled("elastic_enabled")){
			String elasticServerList = XDiamondConfigExtUtil.getProperty("elastic_server_list");
			int max_retries = 3;
			int base_sleep_timems = 1000;
			int max_sleep_timems = 3000;
			String name_space = "elastic_ns";
			
			ZookeeperConfiguration zkConfig = new ZookeeperConfiguration(elasticServerList, name_space, base_sleep_timems, max_sleep_timems, max_retries);
			
			CoordinatorRegistryCenter regCenter = new ZookeeperRegistryCenter(zkConfig);
			regCenter.init();
			
			return regCenter;
		}else{
			return null;
		}
		
	}
}
