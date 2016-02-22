package com.credithc.cache.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 缓存键类。
 * 
 * @author sai.zhang
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface CacheKey {
	/**
	 * jxpath格式访问路径
	 * @return
	 */
	String jxpathAccessor() default "";
}