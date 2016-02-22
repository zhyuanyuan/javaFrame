package com.credithc.cache.impl.redis.serialization;

import java.util.Map;

import com.fasterxml.jackson.databind.PropertyNamingStrategy.PropertyNamingStrategyBase;

/**
 * 属性名称映射策略类。
 * 
 * @author sai.zhang
 */
public class PropertyNameMapStrategy extends PropertyNamingStrategyBase {
	/**
	 * 属性名称映射器
	 */
	private Map<String, String> propertyNameMapper;
	
	public PropertyNameMapStrategy(Map<String, String> propertyNameMapper) {
		this.propertyNameMapper = propertyNameMapper;
	}
	
	@Override
	public String translate(String propertyName) {
		String t = propertyNameMapper.get(propertyName);
		if ( t != null ) {
			return t;
		}
		
		return propertyName;
	}

}
