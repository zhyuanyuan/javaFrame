package com.credithc.sample.core.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import com.credithc.sample.test.support.BackgroundLogsQueue;

public class QueueThreeListener implements MessageListener {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	public void onMessage(Message arg0) {
		BackgroundLogsQueue.appendLogs("rabbit mq : queue three data :" +new String(arg0.getBody()));
		logger.info("rabbit mq : queue three data :" +new String(arg0.getBody()));
	}

}