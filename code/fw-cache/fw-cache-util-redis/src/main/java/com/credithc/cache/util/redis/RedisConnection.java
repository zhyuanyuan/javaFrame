package com.credithc.cache.util.redis;

import redis.clients.jedis.JedisCluster;

/**
 * Redis连接接口定义类。
 * 
 * @author sai.zhang
 */
public interface RedisConnection {
	/**
	 * 获得Jedis对象
	 * @return
	 */
	JedisCluster getJedisCluster();
	
//	/**
//	 * 关闭正常连接
//	 */
//	void close();
//	
//	/**
//	 * 关闭异常连接
//	 */
//	void closeBroken();
//	
//	/**
//	 * 强制关闭连接
//	 */
//	void forceClose();
}