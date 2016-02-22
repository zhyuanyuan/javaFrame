package com.credithc.cache.spring.interceptor;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractBeanFactoryPointcutAdvisor;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.credithc.cache.common.api.CacheWrapper;
import com.credithc.cache.impl.defau1t.description.CacheClass;
import com.credithc.cache.impl.defau1t.description.CacheMethod;
import com.credithc.cache.impl.defau1t.service.CacheService;

/**
 * Bean工厂Cache操作SourceAdvisor类。
 * 
 * @author sai.zhang
 */
public class BeanFactoryCacheOperationSourceAdvisor extends AbstractBeanFactoryPointcutAdvisor implements ApplicationContextAware {
	private static final long serialVersionUID = -6918464663882885072L;

	protected Log logger = LogFactory.getLog(getClass());

	private CacheService cacheService;
	
	private ApplicationContext appCtx;

	protected class CacheClassFilter implements ClassFilter {

		public boolean matches(Class<?> clazz) {
			CacheWrapper cacheWrapper = appCtx.getBean(CacheWrapper.class);
			boolean ret = cacheWrapper.isCacheClass(clazz);
			if (ret == true) {
				CacheClass cacheClass = new CacheClass(clazz);
				Set<String> cacheEntities = new HashSet<String>();
				for (CacheMethod cacheMethod : cacheClass.getCacheMethods().values()) {
					String cacheEntity = cacheMethod.getDescription().getCacheEntity();
					if (cacheEntity == null || cacheEntities.contains(cacheEntity)) {
						continue;
					}

					// 重启，驱逐所有缓存对象，防止对象类更新导致的缓存错误。
					if (logger.isDebugEnabled()) {
						logger.debug("Evict all objects under cacheEntity=[" + cacheEntity + "].");
					}

					cacheEntities.add(cacheEntity);
					cacheService.evictObjects(cacheEntity);
				}

				if (logger.isDebugEnabled()) {
					logger.debug("Wrap cache class=[" + clazz.getName() + "].");
				}
			}

			return ret;
		}

	}

	protected class CacheMethodMatcher implements MethodMatcher {

		public boolean isRuntime() {
			return false;
		}

		public boolean matches(Method method, Class<?> targetClass) {
			CacheClass cacheClazz = new CacheClass(targetClass);
			CacheMethod cacheMethod = cacheClazz.getCacheMethod(method);
			if (cacheMethod != null) {
				if (logger.isDebugEnabled()) {
					logger.debug("Wrap cache class=[" + targetClass.getName() + "], method=[" + method.getName() + "].");
				}

				return true;
			} else {
				return false;
			}
		}

		public boolean matches(Method method, Class<?> targetClass, Object[] args) {
			// should never be invoked because isRuntime() returns false
			throw new UnsupportedOperationException("Illegal MethodMatcher usage");
		}

	}

	protected class CachePointcut implements Pointcut {

		public ClassFilter getClassFilter() {
			return new CacheClassFilter();
		}

		public MethodMatcher getMethodMatcher() {
			return new CacheMethodMatcher();
		}

	}

	public Pointcut getPointcut() {
		return new CachePointcut();
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.appCtx = ctx;
	}

}
