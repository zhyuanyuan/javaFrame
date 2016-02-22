package com.credithc.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.credithc.cache.util.redis.RedisTemplate;
import com.credithc.common.config.XDiamondConfigExtUtil;
import com.credithc.common.mybatis.SerializeUtil;

/**
 * Redis缓存
 * @author jiangxiande
 *
 */
@Component
public class RedisCacheUtils {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisCacheUtils.class);
 
    private static String APP_NAME = XDiamondConfigExtUtil.getProperty("app_name");
    
    private static final String SYS_CACHE = "sysCache";
    
    private static final int TIME_OUT_SECONDS = 300;    //缓存超时时间5分钟
    
    private static RedisTemplate redisTemplate;
    
    private static String getKey(Object key) {
    	return getKey(SYS_CACHE, key);
    }
    
    private static String getKey(String cacheName, Object key) {
    	StringBuilder accum = new StringBuilder();
    	accum.append(APP_NAME).append(":");
    	accum.append(cacheName).append(":");
        accum.append(DigestUtils.md5Hex(String.valueOf(key)));
        return accum.toString();
    }

    /**
	 * 获取SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static Object get(String key) {
		Object value = null;
        try {
            value = SerializeUtil.unserialize(redisTemplate.getValue(getKey(key)));
            logger.debug("RedisCache---->>>>GET FROM CACHE,KEY:" + key);
        } catch (Exception e) {
        	logger.error("Redis cache getObject() error", e);
        }
        return value;
	}
	
	/**
	 * 写入SYS_CACHE缓存
	 * @param key
	 * @return
	 */
	public static void put(String key, Object value) {
		try {
        	redisTemplate.setValue(getKey(key), SerializeUtil.serialize(value));
        	redisTemplate.expire(getKey(key), TIME_OUT_SECONDS);
            logger.debug("RedisCache---->>>>PUT INTO CACHE,KEY:" + key);
        } catch (Exception e) {
            logger.error("Redis cache putObject() error", e);
        }
	}
	
	/**
	 * 从SYS_CACHE缓存中移除
	 * @param key
	 * @return
	 */
	public static void remove(String key) {
        try {
            redisTemplate.removeValue(getKey(key));
            logger.debug("RedisCache---->>>>REMOVE FROM CAHCE,KEY:" + key);
        } catch (Exception e) {
        	logger.error("Redis cache removeObject() error", e);
        }
	}
	
	/**
	 * 获取缓存
	 * @param cacheName
	 * @param key
	 * @return
	 */
	public static Object get(String cacheName, String key) {
		Object value = null;
        try {
            value = SerializeUtil.unserialize(redisTemplate.getValue(getKey(cacheName, key)));
            logger.debug("RedisCache[cacheName=" + cacheName + "]---->>>>GET FROM CACHE,KEY:" + key);
        } catch (Exception e) {
        	logger.error("Redis cache getObject() error", e);
        }
        return value;
	}

	/**
	 * 写入缓存
	 * @param cacheName
	 * @param key
	 * @param value
	 */
	public static void put(String cacheName, String key, Object value) {
		try {
        	redisTemplate.setValue(getKey(cacheName, key), SerializeUtil.serialize(value));
        	redisTemplate.expire(getKey(cacheName, key), TIME_OUT_SECONDS);
            logger.debug("RedisCache[cacheName=" + cacheName + "]---->>>>PUT INTO CACHE,KEY:" + key);
        } catch (Exception e) {
            logger.error("Redis cache putObject() error", e);
        }
	}

	/**
	 * 从缓存中移除
	 * @param cacheName
	 * @param key
	 */
	public static void remove(String cacheName, String key) {
		try {
            redisTemplate.removeValue(getKey(cacheName, key));
            logger.debug("RedisCache[cacheName=" + cacheName + "]---->>>>REMOVE FROM CAHCE,KEY:" + key);
        } catch (Exception e) {
        	logger.error("Redis cache removeObject() error", e);
        }
	}

	@Autowired
	public void setRedisTemplate(RedisTemplate redisTemplate) {
		RedisCacheUtils.redisTemplate = redisTemplate;
	}
	
}
