package com.credithc.common.message;

import org.springframework.amqp.core.AmqpTemplate;

public class MessageHelper {
	
    private AmqpTemplate amqpTemplate;
    
    public MessageHelper(AmqpTemplate amqpTemplate){
    	this.amqpTemplate = amqpTemplate;
    }
	
	public void sendMessage(String exchangeId, String routeKey, Object message){
		amqpTemplate.convertAndSend(exchangeId, routeKey, message);
	}
	
	public void setAmqpTemplate(AmqpTemplate amqpTemplate) {
		this.amqpTemplate = amqpTemplate;
	}

}
