package com.credithc.cache.impl.redis.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class JacksonModuleRegister {
	public static void register(ObjectMapper objMapper) {
		Hibernate4Module module = new Hibernate4Module();
		module.enable(Hibernate4Module.Feature.USE_TRANSIENT_ANNOTATION);
		objMapper.registerModule(module);
	}
}
