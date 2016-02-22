package com.credithc.cache.util.redis;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

/**
 * 缺省Redis模版实现类。
 * 
 * @author sai.zhang
 */
public class DefaultRedisTemplate implements RedisTemplate {
	/**
	 * 数据库索引
	 */
	private Integer dbIndex;

	/**
	 * Redis连接工厂
	 */
 
	private RedisConnectionFactory connectionFactory;

	/**
	 * 连接池
	 */
	private Map<Long, RedisConnection> connections = new ConcurrentHashMap<Long, RedisConnection>();

	protected interface RedisCallback<T> {
		T doInRedis(JedisCluster jedis) throws Throwable;
	}

	protected <T> T execute(RedisCallback<T> action) {
		Assert.notNull(action, "Callback object must not be null");
		RedisConnection conn = connectionFactory.getConnection();

		try {
			connections.put(Thread.currentThread().getId(), conn);

			JedisCluster jedis = conn.getJedisCluster();
			if (dbIndex != null) {
				jedis.select(dbIndex);
			}
			T retObj = action.doInRedis(jedis);
			return retObj;
		} catch (Throwable ex) {
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			connections.remove(Thread.currentThread().getId());
		}
	}

	public void closeConnections(Set<Long> consumerThreadIds) {
		for (long tid : consumerThreadIds) {
			 connections.remove(tid);
		}
	}

	public String setValue(final String key, final String value) {
		return execute(new RedisCallback<String>() {
			public String doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.set(key, value);
			}
		});
	}

	public String getValue(final String key) {
		return execute(new RedisCallback<String>() {
			public String doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.get(key);
			}
		});
	}

	public Long removeValue(final String key) {
		return execute(new RedisCallback<Long>() {
			public Long doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.del(key);
			}
		});
	}

	public Long expire(final String key, final int seconds) {
		return execute(new RedisCallback<Long>() {
			public Long doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.expire(key, seconds);
			}
		});
	}

	public Long ttl(final String key) {
		return execute(new RedisCallback<Long>() {
			public Long doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.ttl(key);
			}
		});
	}

	public boolean exists(final String key) {
		return execute(new RedisCallback<Boolean>() {
			public Boolean doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.exists(key);
			}
		});
	}

	public Set<String> keys(final String kayPattern) {
		return execute(new RedisCallback<Set<String>>() {
			public Set<String> doInRedis(JedisCluster jedis) throws Throwable {
				Set<String>  keySet = new HashSet<String>();
				Map<String, JedisPool> jMap =  jedis.getClusterNodes();
				for (Map.Entry<String, JedisPool>  entry :  jMap.entrySet()) {
					Jedis j = entry.getValue().getResource();
					keySet.addAll(j.keys(kayPattern));
				}
				return keySet;
			}
		});
	}

	public long removeValues(final String keyPattern) {
		return execute(new RedisCallback<Long>() {
			public Long doInRedis(JedisCluster jedis) throws Throwable {
				Set<String>  keys = new HashSet<String>();
				Map<String, JedisPool> jMap =  jedis.getClusterNodes();
				for (Map.Entry<String, JedisPool>  entry :  jMap.entrySet()) {
					Jedis j = entry.getValue().getResource();
					keys.addAll(j.keys(keyPattern));
				}
				for (String key : keys) {
					jedis.del(key);
				}
				return (long) keys.size();
			}
		});
	}

	public String lpop(final String list) {
		return execute(new RedisCallback<String>() {
			public String doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.lpop(list);
			}
		});
	}

	public String lpeek(final String list) {
		return execute(new RedisCallback<String>() {
			public String doInRedis(JedisCluster jedis) throws Throwable {
				List<String> values = jedis.lrange(list, 0, 0);
				if (values.isEmpty()) {
					return null;
				} else {
					return values.get(0);
				}
			}
		});
	}

	public void rpush(final String list, final String value) {
		execute(new RedisCallback<String>() {
			public String doInRedis(JedisCluster jedis) throws Throwable {
				jedis.rpush(list, value);
				return null;
			}
		});
	}

	public String blpop(final int timeout, final String key) {
		return execute(new RedisCallback<String>() {
			public String doInRedis(JedisCluster jedis) throws Throwable {
				List<String> values = jedis.blpop(timeout, key);
				if (values == null) {
					return null;
				}

				if (values.size() < 2) {
					return null;
				}

				return values.get(1);
			}
		});
	}

	public String getSet(final String key, final String value) {
		return execute(new RedisCallback<String>() {
			public String doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.getSet(key, value);
			}
		});
	}

	public void flushDb() {
		execute(new RedisCallback<String>() {
			public String doInRedis(JedisCluster jedis) throws Throwable {
				return jedis.flushDB();
			}
		});
	}

	public Integer getDbIndex() {
		return dbIndex;
	}

	public void setDbIndex(Integer dbIndex) {
		this.dbIndex = dbIndex;
	}

	public RedisConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public void setConnectionFactory(RedisConnectionFactory connectionFactory) {
		this.connectionFactory = connectionFactory;
	}

}
