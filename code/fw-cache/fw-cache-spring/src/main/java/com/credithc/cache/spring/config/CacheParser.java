package com.credithc.cache.spring.config;

import org.springframework.aop.config.AopNamespaceUtils;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.springframework.util.xml.DomUtils;
import org.w3c.dom.Element;

import com.credithc.cache.impl.defau1t.service.DefaultCacheServiceImpl;
import com.credithc.cache.spring.SpringCacheWrapper;
import com.credithc.cache.spring.interceptor.BeanFactoryCacheOperationSourceAdvisor;
import com.credithc.cache.spring.interceptor.CacheMethodInterceptor;
import com.credithc.cache.spring.util.PropertiesHolder;

/**
 * Cache配置标签解释器类。
 * 
 * @author sai.zhang
 */
public class CacheParser extends AbstractSingleBeanDefinitionParser {
	public static final String CACHE_ADVISOR_BEAN_NAME = "com.credithc.framework.cache.internalCacheAdvisor";

	private static final String REDIS_CACHE_PROVIDER = "redis-cache-provider";

	@Override
	protected Class<?> getBeanClass(Element element) {
		return SpringCacheWrapper.class;
	}

	@Override
	protected void doParse(Element element, ParserContext parserContext, BeanDefinitionBuilder builder) {
		PropertiesHolder holder = new PropertiesHolder();

		Element childElement = DomUtils.getChildElementByTagName(element, REDIS_CACHE_PROVIDER);
		RootBeanDefinition cacheProviderDef = null;
		if (childElement != null) {
			cacheProviderDef = RedisCacheServiceParser.doParse(childElement, parserContext, holder);
		} else {
			throw new RuntimeException("The cacheProvider is required!");
		}
		
		RootBeanDefinition defaultCacheServiceImplDef = new RootBeanDefinition(DefaultCacheServiceImpl.class);
		defaultCacheServiceImplDef.setSource(element);
		defaultCacheServiceImplDef.setRole(BeanDefinition.ROLE_APPLICATION);
		
		defaultCacheServiceImplDef.getPropertyValues().add("cacheProvider", cacheProviderDef);
		
		builder.addPropertyValue("cacheService", defaultCacheServiceImplDef);
		builder.addPropertyValue("beans", holder.getBeans());
		builder.addPropertyValue("propertyNames", holder.getPropertyNames());
		builder.addPropertyValue("propertyValues", holder.getPropertyValues());

		AopNamespaceUtils.registerAutoProxyCreatorIfNecessary(parserContext, element);
		
		Object eleSource = parserContext.extractSource(element);
		// Create the CacheInterceptor definition.
		RootBeanDefinition interceptorDef = new RootBeanDefinition(CacheMethodInterceptor.class);
		interceptorDef.setSource(eleSource);
		interceptorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		interceptorDef.getPropertyValues().add("cacheService", defaultCacheServiceImplDef);
		String interceptorName = parserContext.getReaderContext().registerWithGeneratedName(interceptorDef);

		// Create the CacheAdvisor definition.
		RootBeanDefinition advisorDef = new RootBeanDefinition(BeanFactoryCacheOperationSourceAdvisor.class);
		advisorDef.setSource(eleSource);
		advisorDef.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
		advisorDef.getPropertyValues().add("adviceBeanName", interceptorName);
		advisorDef.getPropertyValues().add("cacheService", defaultCacheServiceImplDef);
		
		if (element.hasAttribute("order")) {
			advisorDef.getPropertyValues().add("order", element.getAttribute("order"));
		}

		parserContext.getRegistry().registerBeanDefinition(CACHE_ADVISOR_BEAN_NAME, advisorDef);
	}

	@Override
	protected String getBeanClassName(Element element) {
		return element.getAttribute("id");
	}
}
