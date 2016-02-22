package com.credithc.sample.test.support;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackgroundLogsQueue {
	private static final Logger logger = LoggerFactory.getLogger(BackgroundLogsQueue.class);
	
	public static final BlockingQueue<String> logsQ = new ArrayBlockingQueue<String>(1000);
	
	public static synchronized void appendLogs(String logs){
		try {
			while(!logsQ.offer(logs)){
				String s = logsQ.poll();
				if(StringUtils.isNotBlank(s)){
					logger.warn("ignore logs,because the q is full..." + s);
				}
			}
		} catch (Exception e) {
			logger.error("put error", e);
		}
	}
	
	public static synchronized String fetchLogs(){
		StringBuilder sb = new StringBuilder();
		while(true){
			String s = logsQ.poll();
			if(s != null){
				sb.append(s + "<br>");
			}else{
				break;
			}
		}
		return sb.toString();
	}

}
