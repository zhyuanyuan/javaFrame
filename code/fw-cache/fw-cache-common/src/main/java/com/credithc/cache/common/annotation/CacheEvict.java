package com.credithc.cache.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 缓存驱逐标签类。
 * 
 * @author sai.zhang
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface CacheEvict {
	/**
	 * 缓存实体名称
	 * @return
	 */
	String entity() default "";
	
	/**
	 * 缓存实体类
	 * @return
	 */
	Class<?> cacheObject() default NotDefine.class;

	/**
	 * 缓存键值，jxpath表达式
	 * @return
	 */
	String[] keys() default {};
}