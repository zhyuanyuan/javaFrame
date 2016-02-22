package com.credithc.cache.impl.redis;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.credithc.cache.common.api.CacheProvider;
import com.credithc.cache.common.api.NotFoundCachedException;
import com.credithc.cache.common.api.ObjectHolder;
import com.credithc.cache.impl.redis.description.RedisObjectHolder;
import com.credithc.cache.impl.redis.serialization.JacksonSerializer;
import com.credithc.cache.util.redis.RedisTemplate;

/**
 * 基于Redis实现的Cache服务类。
 * 
 * @author sai.zhang
 */
public class RedisCacheProvider implements CacheProvider {
	public Log logger = LogFactory.getLog(getClass());

	public static final String REDIS_KEY_PREFIX = "credithc:cache:";

	/**
	 * 缺省ttl为5分钟
	 */
	private int defaultTtl = 5 * 60;

	/**
	 * Redis模版
	 */
	private RedisTemplate redisTemplate;

	/**
	 * jacksonSerializer器
	 */
	private JacksonSerializer jacksonSerializer = new JacksonSerializer(false);

	private String getRedisKey(String entity, String key) {
		StringBuffer sb = new StringBuffer();
		sb.append(REDIS_KEY_PREFIX);
		sb.append(entity);
		sb.append("(");
		sb.append(key);
		sb.append(")");

		return sb.toString();
	}

	private RedisObjectHolder convertToRedisObjectHolder(ObjectHolder objHolder) {
		RedisObjectHolder rObjHolder = new RedisObjectHolder();

		rObjHolder.setV(objHolder.getObjectVersion());
		rObjHolder.setCk(objHolder.getObjectChecksum());
		Object obj = objHolder.getObject();

		if (obj == null) {
			rObjHolder.setC(String.class.getName());
		} else {
			rObjHolder.setC(obj.getClass().getName());
		}

		try {
			rObjHolder.setD(new String(jacksonSerializer.serialize(obj), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		return rObjHolder;
	}

	private ObjectHolder convertToObjectHolder(RedisObjectHolder rObjHolder,
			Type[] expectClasses) throws NotFoundCachedException {
		ObjectHolder objHolder = new ObjectHolder();

		objHolder.setObjectVersion(rObjHolder.getV());

		Object obj;
		Class<?> clazz;
		try {
			clazz = Class.forName(rObjHolder.getC());
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		Type expectClass = null;
		if (expectClasses != null) {
			for (int i = 0; i < expectClasses.length; i++) {
				if (clazz.equals(expectClasses[i])) {
					expectClass = expectClasses[i];
					break;
				}
			}

			if (expectClass == null) {
				expectClass = expectClasses[expectClasses.length - 1];
			}
		} else {
			expectClass = clazz;
		}

		try {
			obj = jacksonSerializer.deserialize(expectClass,
					rObjHolder.getD().getBytes("UTF-8"));
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		objHolder.setObject(obj);
		objHolder.setObjectChecksum(rObjHolder.getCk());

		return objHolder;
	}

	public ObjectHolder getCachedObject(String entity, String key, Type[] expectClasses)
			throws NotFoundCachedException {
		try {
			String redisKey = getRedisKey(entity, key);
			String jsonData = redisTemplate.getValue(redisKey);
			if (jsonData == null) {
				throw new NotFoundCachedException();
			}

			RedisObjectHolder redisObjHolder = jacksonSerializer.deserialize(
					RedisObjectHolder.class, jsonData.getBytes("UTF-8"));
			return convertToObjectHolder(redisObjHolder, expectClasses);
		} catch (NotFoundCachedException e) {
			throw e;
		} catch (Throwable e) {
			// 屏蔽Cache功能非正常状态，不影响正常业务逻辑处理
			logger.error("RedisCacheProvider.getCacheReturn meet error.", e);
			throw new NotFoundCachedException();
		}
	}

	public void cacheObject(String entity, String key, ObjectHolder obj, int ttl) {
		try {
			String redisKey = getRedisKey(entity, key);
			RedisObjectHolder rObjHolder = convertToRedisObjectHolder(obj);

			String jsonData = new String(jacksonSerializer.serialize(rObjHolder), "UTF-8");
			redisTemplate.setValue(redisKey, jsonData);
			if (ttl < 0) {
				// 不支持无限制缓存，将ttl设置为缺省ttl
				logger.warn("Adjust the ttl=[" + ttl + "] to defaultTtl=[" + defaultTtl
						+ "], redisKey=[" + redisKey + "].");
				ttl = defaultTtl;
			}

			redisTemplate.expire(redisKey, ttl);
		} catch (Throwable e) {
			// 屏蔽Cache功能非正常状态，不影响正常业务逻辑处理
			logger.error("RedisCacheProvider.cacheReturn meet error.", e);
		}
	}

	public ObjectHolder evictObject(String entity, String key) {
		try {
			String redisKey = getRedisKey(entity, key);
			String jsonData = redisTemplate.getValue(redisKey);
			if (jsonData == null) {
				return null;
			}

			redisTemplate.removeValue(redisKey);
			RedisObjectHolder redisObjHolder = jacksonSerializer.deserialize(
					RedisObjectHolder.class, jsonData.getBytes("UTF-8"));
			return convertToObjectHolder(redisObjHolder, null);
		} catch (Throwable e) {
			// 屏蔽Cache功能非正常状态，不影响正常业务逻辑处理
			logger.error("RedisCacheProvider.evictObject meet error.", e);
			return null;
		}
	}

	public long evictObjects(String entity) {
		try {
			String redisKeyPattern = REDIS_KEY_PREFIX + entity + "(*)";
			return redisTemplate.removeValues(redisKeyPattern);
		} catch (Throwable e) {
			// 屏蔽Cache功能非正常状态，不影响正常业务逻辑处理
			logger.error("RedisCacheProvider.evictObject meet error.", e);
			return 0;
		}
	}

	public int getDefaultTtl() {
		return defaultTtl;
	}

	public void setDefaultTtl(int defaultTtl) {
		this.defaultTtl = defaultTtl;
	}

	public RedisTemplate getRedisTemplate() {
		return redisTemplate;
	}

	public void setRedisTemplate(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

}