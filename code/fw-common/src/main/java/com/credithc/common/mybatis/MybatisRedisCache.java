package com.credithc.common.mybatis;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.ibatis.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.cache.util.redis.DefaultRedisConnectionFactory;
import com.credithc.cache.util.redis.DefaultRedisTemplate;
import com.credithc.cache.util.redis.RedisTemplate;
import com.credithc.common.config.XDiamondConfigExtUtil;

public class MybatisRedisCache implements Cache {
	private static final Logger logger = LoggerFactory.getLogger(MybatisRedisCache.class);
    
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
 
    private final String COMMON_CACHE_KEY = "COM:";
    
    private RedisTemplate redisTemplate;
  
    private String getKey(Object key) {
        StringBuilder accum = new StringBuilder();
        accum.append(COMMON_CACHE_KEY);
        accum.append(this.id).append(":");
        accum.append(DigestUtils.md5Hex(String.valueOf(key)));
        return accum.toString();
    }
  
    private String getKeys() {
        return COMMON_CACHE_KEY + this.id + ":*";
    }
 
    private String id;
 
    {
		try {
			String redisHost = XDiamondConfigExtUtil.getProperty("redis_host");
			int redisPort = Integer.parseInt(XDiamondConfigExtUtil.getProperty("redis_port"));
		
    	
	    	DefaultRedisConnectionFactory redisConnectionFactory = new DefaultRedisConnectionFactory();
	    	redisConnectionFactory.setHost(redisHost);
	    	redisConnectionFactory.setPort(redisPort);
	    	redisConnectionFactory.init();
	    	
	    	DefaultRedisTemplate defaultRedisTemplate = new DefaultRedisTemplate();
	    	defaultRedisTemplate.setConnectionFactory(redisConnectionFactory);
	    	redisTemplate = defaultRedisTemplate;
		} catch (Exception e) {
			logger.error("MybatisRedisCache[id=" + id + "]---->>>>INIT", e);
		} 
    }
     
    public MybatisRedisCache() {
    	
    }
 
    public MybatisRedisCache(final String id) {
        if (id == null) {
            throw new IllegalArgumentException("必须传入ID");
        }
        logger.debug("MybatisRedisCache[id=" + id + "]---->>>>CREATE CACHE");
        this.id = id;
    }
 
    @Override
    public String getId() {
        return this.id;
    }
 
    @Override
    public int getSize() {
        int result = 0;
        try {
        	result = redisTemplate.keys(getKeys()).size();
            logger.debug("MybatisRedisCache[id=" + id + "]---->>>>CACHE SIZE:" + result);
        } catch (Exception e) {
        	logger.error("mybatis redis cache getSize() error", e);
        } 
        return result;
 
    }
 
    @Override
    public void putObject(Object key, Object value) {
        try {
        	redisTemplate.setValue(getKey(key), SerializeUtil.serialize(value));
            logger.debug("MybatisRedisCache[id=" + id + "]---->>>>PUT INTO CACHE,KEY:" + key);
        } catch (Exception e) {
            logger.error("mybatis redis cache putObject() error", e);
        }
    }
 
    @Override
    public Object getObject(Object key) {
        Object value = null;
        try {
            value = SerializeUtil.unserialize(redisTemplate.getValue(getKey(key)));
            logger.debug("MybatisRedisCache[id=" + id + "]---->>>>GET FROM CACHE,KEY:" + key);
        } catch (Exception e) {
        	logger.error("mybatis redis cache getObject() error", e);
        }
        return value;
    }
 
    @Override
    public Object removeObject(Object key) {
        Object value = null;
        try {
            value = redisTemplate.removeValue(getKey(key));
            logger.debug("MybatisRedisCache[id=" + id + "]---->>>>REMOVE FROM CAHCE,KEY:" + key);
        } catch (Exception e) {
        	logger.error("mybatis redis cache removeObject() error", e);
        }
        return value;
    }
 
    @Override
    public void clear() {
        try {
        	long keyCount = redisTemplate.removeValues(getKeys());
        	logger.debug("MybatisRedisCache[id=" + id + "]---->>>>CLEAR CACHE,TOTAL COUNT:" + keyCount);
        } catch (Exception e) {
        	logger.error("mybatis redis cache clear() error", e);
        }
    }
 
    @Override
    public ReadWriteLock getReadWriteLock() {
        return readWriteLock;
    }

}
