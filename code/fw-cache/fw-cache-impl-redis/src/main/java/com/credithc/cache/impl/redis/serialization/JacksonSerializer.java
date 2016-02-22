package com.credithc.cache.impl.redis.serialization;

import java.lang.reflect.Type;
import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.smile.SmileFactory;

/**
 * JacksonSmile实现的序列化器类。
 * 
 * @author sai.zhang
 */
public class JacksonSerializer implements Serializer {
	private static ObjectMapper jacksonMapper;

	private static ObjectMapper prettyJacksonMapper;

	private static ObjectMapper jacksonSmileMapper;

	private static boolean hibMod = false;

	public static class GenericTypeReference extends TypeReference<String> {
		private Type type;

		public GenericTypeReference(Type type) {
			this.type = type;
		}

		@Override
		public Type getType() {
			return type;
		}

		@Override
		public int compareTo(TypeReference<String> o) {
			return 0;
		}

	}

	static {
		try {
			Class.forName("com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module");
			hibMod = true;
		} catch (ClassNotFoundException e) {
		}

		jacksonSmileMapper = new ObjectMapper(new SmileFactory());
		jacksonSmileMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		jacksonSmileMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		if (hibMod) {
			JacksonModuleRegister.register(jacksonSmileMapper);
		}

		jacksonMapper = new ObjectMapper();
		jacksonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		jacksonMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		if (hibMod) {
			JacksonModuleRegister.register(jacksonMapper);
		}

		prettyJacksonMapper = new ObjectMapper();
		prettyJacksonMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		prettyJacksonMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		prettyJacksonMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		if (hibMod) {
			JacksonModuleRegister.register(prettyJacksonMapper);
		}
	}

	private ObjectMapper mapper;

	public JacksonSerializer(boolean smileFlag) {
		if (smileFlag) {
			mapper = jacksonSmileMapper;
		} else {
			mapper = jacksonMapper;
		}
	}

	private JacksonSerializer() {
	}

	public static JacksonSerializer newPrettySerializer() {
		JacksonSerializer ser = new JacksonSerializer();
		ser.mapper = prettyJacksonMapper;

		return ser;
	}

	public static JacksonSerializer newSerializer(Map<String, String> propertyNameMapper) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.setPropertyNamingStrategy(new PropertyNameMapStrategy(propertyNameMapper));

		if (hibMod) {
			JacksonModuleRegister.register(mapper);
		}

		JacksonSerializer ser = new JacksonSerializer();
		ser.mapper = mapper;

		return ser;
	}

	public static JacksonSerializer newSmileSerializer(Map<String, String> propertyNameMapper) {
		ObjectMapper mapper = new ObjectMapper(new SmileFactory());
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		mapper.setPropertyNamingStrategy(new PropertyNameMapStrategy(propertyNameMapper));

		if (hibMod) {
			JacksonModuleRegister.register(mapper);
		}

		JacksonSerializer ser = new JacksonSerializer();
		ser.mapper = mapper;

		return ser;
	}

	public byte[] serialize(Object bean) {
		try {
			return mapper.writeValueAsBytes(bean);
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public <T> T deserialize(Class<T> clazz, byte[] data) {
		try {
			return (T) mapper.readValue(data, clazz);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public Object deserialize(Type type, byte[] data) {
		try {
			if (type instanceof Class) {
				return deserialize((Class<?>) type, data);
			}

			GenericTypeReference typeRef = new GenericTypeReference(type);
			return mapper.readValue(data, typeRef);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public byte[] serialize(Type type, Object bean) {
		return serialize(bean);
	}

	public String serializeAsString(Type type, Object bean) {
		return new String(serialize(type, bean));
	}

	public String serializeAsString(Object bean) {
		return new String(serialize(bean));
	}

	public <T> T deserialize(Class<T> clazz, String data) {
		return deserialize(clazz, data.getBytes());
	}

	public Object deserialize(Type type, String data) {
		return deserialize(type, data.getBytes());
	}

}
