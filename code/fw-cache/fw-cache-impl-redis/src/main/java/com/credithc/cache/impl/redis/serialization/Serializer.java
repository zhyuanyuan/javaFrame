package com.credithc.cache.impl.redis.serialization;

import java.lang.reflect.Type;

/**
 * 序列化器接口定义类。
 * 
 * @author sai.zhang
 */
public interface Serializer {
	byte[] serialize(Object bean);
	
	byte[] serialize(Type type, Object bean);
	
	String serializeAsString(Type type, Object bean);
	
	String serializeAsString(Object bean);

	<T> T deserialize(Class<T> clazz, byte[] data);
	
	Object deserialize(Type type, byte[] data);
	
	<T> T deserialize(Class<T> clazz, String data);
	
	Object deserialize(Type type, String data);
}
