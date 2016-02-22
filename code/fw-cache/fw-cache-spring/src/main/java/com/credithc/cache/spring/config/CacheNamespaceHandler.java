package com.credithc.cache.spring.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Cache名字空间处理器实现类。
 * 
 * @author sai.zhang
 */
public class CacheNamespaceHandler extends NamespaceHandlerSupport {
	public void init() {
		this.registerBeanDefinitionParser("cache", new CacheParser());
	}

}