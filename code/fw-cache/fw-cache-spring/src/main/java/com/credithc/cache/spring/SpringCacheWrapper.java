package com.credithc.cache.spring;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

import com.credithc.cache.impl.defau1t.DefaultCacheWrapper;
import com.credithc.cache.spring.util.PropertyUtil;

/**
 * 基于Spring的缓存包装器类。
 * 
 * @author sai.zhang
 */
public class SpringCacheWrapper extends DefaultCacheWrapper implements InitializingBean, BeanPostProcessor,
		PriorityOrdered {
	protected Log logger = LogFactory.getLog(this.getClass());

	/**
	 * 保留字段，拥有NestedBean属性设置
	 */
	private List<Object> beans;

	/**
	 * 保留字段，拥有NestedBean属性设置
	 */
	private List<String> propertyNames;

	/**
	 * 保留字段，拥有NestedBean属性设置
	 */
	private List<String> propertyValues;

	public void afterPropertiesSet() throws Exception {
		logger.info("Init fw-Cache.");
		PropertyUtil.setProperties(beans, propertyNames, propertyValues);
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
	
	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}

	public int getOrder() {
		return Ordered.LOWEST_PRECEDENCE;
	}

	public List<Object> getBeans() {
		return beans;
	}

	public void setBeans(List<Object> beans) {
		this.beans = beans;
	}

	public List<String> getPropertyNames() {
		return propertyNames;
	}

	public void setPropertyNames(List<String> propertyNames) {
		this.propertyNames = propertyNames;
	}

	public List<String> getPropertyValues() {
		return propertyValues;
	}

	public void setPropertyValues(List<String> propertyValues) {
		this.propertyValues = propertyValues;
	}

}
