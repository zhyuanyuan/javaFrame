package com.credithc.cache.util.redis;

/**
 * Redis实现的连接工厂接口定义类。
 * 
 * @author sai.zhang
 */
public interface RedisConnectionFactory {
	/**
	 * 获得Redis连接
	 * @return
	 */
	RedisConnection getConnection();
}
