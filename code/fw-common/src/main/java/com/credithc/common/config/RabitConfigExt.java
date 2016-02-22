package com.credithc.common.config;

import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.credithc.common.message.FastJsonMessageConverter;


@Configuration
public class RabitConfigExt extends BaseConfigExt{
	
	@Autowired
	@Qualifier("jsonMessageConverter")
	FastJsonMessageConverter jsonMessageConverter;
	
	@Autowired
	@Qualifier("rabbitConnectionFactory")
	ConnectionFactory rabbitConnectionFactory;
	
	@Bean(name="rabbitConnectionFactory") 
    public ConnectionFactory rabbitConnectionFactory() {  
		if(super.isEnabled("rabbit_enabled")){
			CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
			String rabbitHost = XDiamondConfigExtUtil.getProperty("rabbit_mq_host");
			String rabbitPort = XDiamondConfigExtUtil.getProperty("rabbit_mq_port");
			String rabbitUser = XDiamondConfigExtUtil.getProperty("rabbit_mq_user");
			String rabbitPwd = XDiamondConfigExtUtil.getProperty("rabbit_mq_pwd");
			
			logger.info("rabbit_mq_host:" + rabbitHost);
			logger.info("rabbit_mq_port:" + rabbitPort);
			logger.info("rabbit_mq_user:" + rabbitUser);
			logger.info("rabbit_mq_pwd:" + rabbitPwd);
			
			connectionFactory.setHost(rabbitHost);
			connectionFactory.setPort(Integer.parseInt(rabbitPort));
			connectionFactory.setUsername(rabbitUser);
	        connectionFactory.setPassword(rabbitPwd);  
	        
	        return connectionFactory;
		}else{
			return null;
		}
    }  
	
	@Bean(name="jsonMessageConverter") 
	public FastJsonMessageConverter jsonMessageConverter(){
		FastJsonMessageConverter jsonMessageConverter = new FastJsonMessageConverter();
		return jsonMessageConverter;
	}
	
	@Bean 
	public RabbitAdmin connectionAdmin() {
		if(super.isEnabled("rabbit_enabled")){
			RabbitAdmin admin = new RabbitAdmin(rabbitConnectionFactory);
			return admin;
		}else{
			return null;
		}
	}
	
	@Bean
	public RabbitTemplate rabitTemplateBean() {
		if(super.isEnabled("rabbit_enabled")){
			RabbitTemplate template = new RabbitTemplate(rabbitConnectionFactory);
			template.setMessageConverter(jsonMessageConverter);
			return template;
		}else{
			return null;
		}
	}
  
}
