package com.credithc.cache.util.redis;

import org.apache.commons.logging.Log;

import org.apache.commons.logging.LogFactory;

import redis.clients.jedis.JedisCluster;

/**
 * 缺省Redis连接实现类。
 * 
 * @author sai.zhang
 */
public class DefaultRedisConnection implements RedisConnection {
	private Log logger = LogFactory.getLog(getClass());
	
	/**
	 * Jedis对象
	 */
	private JedisCluster jedisCluster;

	public DefaultRedisConnection(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}
	

	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

}
