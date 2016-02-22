package com.credithc.cache.spring.config;

import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;

import com.credithc.cache.impl.redis.RedisCacheProvider;
import com.credithc.cache.spring.util.PropertiesHolder;
import com.credithc.cache.util.redis.DefaultRedisConnectionFactory;
import com.credithc.cache.util.redis.DefaultRedisTemplate;

/**
 * Redis缓存服务配置解析器类。
 * 
 * @author sai.zhang
 */
public class RedisCacheServiceParser {
	private static final String CONNECTION_CACHE_SIZE_ATTR = "connection-cache-size";

	private static final String CONNECTION_MAX_SIZE_ATTR = "connection-max-size";

	private static final String HOST_ATTR = "host";

	private static final String PORT_ATTR = "port";

	private static final String PASSWORD_ATTR = "password";

	private static final String TIMEOUT_ATTR = "timeout";

	private static final String DATABASE_ATTR = "database";

	private static final String DEFAULT_TTL_ATTR = "default-ttl";

	private static final String JEDIS_CLUSTER = "jedisCluster";

	protected static void addBeanProperty(RootBeanDefinition beanDef, Element element, String attrName, String propName) {
		String value = StringUtils.trimToNull(element.getAttribute(attrName));
		if (value != null) {
			beanDef.getPropertyValues().add(propName, value);
		}
	}

	public static RootBeanDefinition doParse(Element element, ParserContext parserContext, PropertiesHolder holder) {
		RootBeanDefinition defaultRedisConnectionFactoryDef = createRedisConnectionFactoryDef(element, parserContext,
				holder);

		RootBeanDefinition defaultRedisTemplateDef = new RootBeanDefinition(DefaultRedisTemplate.class);
		defaultRedisTemplateDef.setSource(element);
		defaultRedisTemplateDef.setRole(BeanDefinition.ROLE_APPLICATION);

		defaultRedisTemplateDef.getPropertyValues().add("connectionFactory", defaultRedisConnectionFactoryDef);

		RootBeanDefinition redisCacheProviderDef = new RootBeanDefinition(RedisCacheProvider.class);
		redisCacheProviderDef.setSource(element);
		redisCacheProviderDef.setRole(BeanDefinition.ROLE_APPLICATION);

		redisCacheProviderDef.getPropertyValues().add("redisTemplate", defaultRedisTemplateDef);
		addBeanProperty(redisCacheProviderDef, element, DEFAULT_TTL_ATTR, "defaultTtl");

		return redisCacheProviderDef;
	}

	protected static RootBeanDefinition createRedisConnectionFactoryDef(Element element, ParserContext parserContext,
			PropertiesHolder holder) {
		RootBeanDefinition redisConnectionFactoryDef = null;
		redisConnectionFactoryDef = new RootBeanDefinition(DefaultRedisConnectionFactory.class );
		redisConnectionFactoryDef.setSource(element);
		redisConnectionFactoryDef.setScope(BeanDefinition.SCOPE_SINGLETON);
		redisConnectionFactoryDef.setRole(BeanDefinition.ROLE_APPLICATION);
		addBeanProperty(redisConnectionFactoryDef, element, HOST_ATTR, "host");
		addBeanProperty(redisConnectionFactoryDef, element, PORT_ATTR, "port");
		addBeanProperty(redisConnectionFactoryDef, element, PASSWORD_ATTR, "password");
		addBeanProperty(redisConnectionFactoryDef, element, TIMEOUT_ATTR, "timeout");
		addBeanProperty(redisConnectionFactoryDef, element, DATABASE_ATTR, "database");
		redisConnectionFactoryDef.getPropertyValues().add(JEDIS_CLUSTER, getJedisCluster(element.getAttribute(HOST_ATTR),element.getAttribute(PORT_ATTR),element.getAttribute(TIMEOUT_ATTR),element.getAttribute(CONNECTION_CACHE_SIZE_ATTR),element.getAttribute(CONNECTION_MAX_SIZE_ATTR)));
		return redisConnectionFactoryDef;
	}
	
	private static JedisCluster  getJedisCluster(String host,String port,String timeout,String connectionCacheSize,String connectionMaxSize ) {
		 GenericObjectPoolConfig  genericObjectPoolConfig = new GenericObjectPoolConfig();
		 genericObjectPoolConfig.setMaxTotal(Integer.valueOf(connectionMaxSize));//最大连接数
		 genericObjectPoolConfig.setMinIdle(Integer.valueOf(connectionCacheSize));//最小空闲连接数
		 genericObjectPoolConfig.setTestOnBorrow(true);//在获取连接的时候检查有效性, 默认false
		 HostAndPort hostAndPort = new HostAndPort(host,Integer.valueOf(port));
		 Set<HostAndPort> hset = new HashSet<HostAndPort>();
		 hset.add(hostAndPort);
		return new JedisCluster(hset,Integer.valueOf(timeout),50,genericObjectPoolConfig);
		
	}
}
