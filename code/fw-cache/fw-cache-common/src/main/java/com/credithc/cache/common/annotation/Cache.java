package com.credithc.cache.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 缓冲控制标签定义类。
 * 
 * @author sai.zhang
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface Cache {
	/**
	 * 缺省缓存时间周期，1个小时
	 */
	public static final int DEFAULT_TTL = 60*60;

	/**
	 * 缓存生存周期（秒），当policy=TTL时生效，缺省
	 * 
	 * @return
	 */
	int ttl() default DEFAULT_TTL;

	/**
	 * 缓存实体名称，缺省使用ServiceId+MethodName。
	 * @return
	 */
	String entity() default "";
	
	/**
	 * 缓存键值，jxpath表达式
	 * @return
	 */
	String[] keys() default {};
	
	/**
	 * 缓存Null数值
	 * @return
	 */
	boolean cacheNull() default false;
}