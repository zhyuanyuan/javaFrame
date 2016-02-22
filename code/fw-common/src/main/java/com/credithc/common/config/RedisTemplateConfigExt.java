package com.credithc.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.credithc.cache.util.redis.DefaultRedisConnectionFactory;
import com.credithc.cache.util.redis.DefaultRedisTemplate;
import com.credithc.cache.util.redis.RedisConnectionFactory;

@Configuration
public class RedisTemplateConfigExt extends BaseConfigExt {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("redisConnectionFactory")
	private RedisConnectionFactory redisConnectionFactory;
	
	@Bean(name="redisTemplate")
	public DefaultRedisTemplate regRedisTemplate(){
		if(super.isEnabled("redis_enabled")){
			DefaultRedisTemplate redisTemplate = new DefaultRedisTemplate();
			redisTemplate.setConnectionFactory(redisConnectionFactory);
			return redisTemplate;
		}else{
			return null;
		}
	}
	
	@Bean(name="redisConnectionFactory")
	public RedisConnectionFactory regRedisConnectionFactory(){
		if(super.isEnabled("redis_enabled")){
			String host = XDiamondConfigExtUtil.getProperty("redis_host");
			String port = XDiamondConfigExtUtil.getProperty("redis_port");
			logger.info("redis_host:" + host);
			logger.info("redis_port:" + port);
			DefaultRedisConnectionFactory redisConnectionFactory = new DefaultRedisConnectionFactory();
			redisConnectionFactory.setHost(host);
			redisConnectionFactory.setPort(Integer.parseInt(port));
			redisConnectionFactory.init();
			return redisConnectionFactory;
		}else{
			return null;
		}
	}

}
