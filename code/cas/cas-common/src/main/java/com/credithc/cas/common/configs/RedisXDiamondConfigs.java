package com.credithc.cas.common.configs;

/**
 * cas基于redis集群相关配置
 * 
 * @author yangyang151020
 *
 */
public class RedisXDiamondConfigs {

	private String redisHost;// redis服务器HOST
	private int redisPort;// redis端口
	private int stTimeOut;// serviceTicket超时时间
	private int tgtTimeOut;// ticketGrantingTicket超时时间

	public String getRedisHost() {
		return redisHost;
	}

	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}

	public int getRedisPort() {
		return redisPort;
	}

	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}

	public int getStTimeOut() {
		return stTimeOut;
	}

	public void setStTimeOut(int stTimeOut) {
		this.stTimeOut = stTimeOut;
	}

	public int getTgtTimeOut() {
		return tgtTimeOut;
	}

	public void setTgtTimeOut(int tgtTimeOut) {
		this.tgtTimeOut = tgtTimeOut;
	}

	public RedisXDiamondConfigs() {
		super();
	}
	
}
