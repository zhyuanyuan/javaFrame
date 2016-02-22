package com.credithc.cache.util.redis;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.Protocol;

public class DefaultRedisConnectionFactory implements RedisConnectionFactory{
	/**
	 * 缺省连接缓存尺寸
	 */
	public static final int DEFAULT_CONNECTION_CACHE_SIZE = 2;
	
	/**
	 * 缺省连接最大数量
	 */
	public static final int DEFAULT_CONNECTION_MAX_SIZE = 5;
	
	/**
	 * jedis Cluster
	 */
//	@Autowired
	private JedisCluster jedisCluster;
 
	public JedisCluster getJedisCluster() {
		return jedisCluster;
	}

	public void setJedisCluster(JedisCluster jedisCluster) {
		this.jedisCluster = jedisCluster;
	}

	/**
	 * Redis主机
	 */
	private String host = "127.0.0.1";

	/**
	 * Redis端口
	 */
	private int port = 7000;

	/**
	 * 超时（毫秒）
	 */
	private long timeout = Protocol.DEFAULT_TIMEOUT;

	/**
	 * 密码
	 */
	private String password;

	/**
	 * 数据库
	 */
	private int database = Protocol.DEFAULT_DATABASE;
	

	public void init() {
//		if ( connectionMaxSize < connectionCacheSize ) {
//			connectionMaxSize = connectionCacheSize;
//		}
		 GenericObjectPoolConfig  genericObjectPoolConfig = new GenericObjectPoolConfig();
		 genericObjectPoolConfig.setMaxWaitMillis(1000);
		 genericObjectPoolConfig.setMaxTotal(1000  );
		 genericObjectPoolConfig.setMinIdle(10);
		 genericObjectPoolConfig.setTestOnBorrow(true);
		 HostAndPort hostAndPort = new HostAndPort(this.getHost(),this.getPort());
		 Set<HostAndPort> hset = new HashSet<HostAndPort>();
		 hset.add(hostAndPort);
		jedisCluster= new JedisCluster(hset,2000,50,genericObjectPoolConfig);
		
	}

	public RedisConnection getConnection() {
		return new DefaultRedisConnection(jedisCluster);
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public long getTimeout() {
		return timeout;
	}

	public void setTimeout(long timeout) {
		this.timeout = timeout;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getDatabase() {
		return database;
	}

	public void setDatabase(int database) {
		this.database = database;
	}


}
