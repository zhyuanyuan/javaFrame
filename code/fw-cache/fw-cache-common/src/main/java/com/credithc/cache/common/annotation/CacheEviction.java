package com.credithc.cache.common.annotation;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 缓存控制标签类。
 * 
 * @author sai.zhang
 */
@Target(METHOD)
@Retention(RUNTIME)
public @interface CacheEviction {
	CacheEvict[] value();
}