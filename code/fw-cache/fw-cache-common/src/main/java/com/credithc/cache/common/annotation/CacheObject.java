package com.credithc.cache.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 缓存对象标签。
 * 
 * @author sai.zhang
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface CacheObject {
	/**
	 * 实体名称，缺省使用类名称。
	 * @return
	 */
	String entity() default "";
	
	/**
	 * 生存周期（秒）
	 * @return
	 */
	int ttl() default Cache.DEFAULT_TTL;
}
