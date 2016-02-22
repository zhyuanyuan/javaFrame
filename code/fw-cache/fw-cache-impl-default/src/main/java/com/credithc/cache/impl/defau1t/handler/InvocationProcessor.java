package com.credithc.cache.impl.defau1t.handler;

import java.lang.reflect.Method;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.credithc.cache.common.api.HookExecutor;
import com.credithc.cache.common.api.HookHandler;
import com.credithc.cache.common.api.NotFoundCachedException;
import com.credithc.cache.impl.defau1t.description.CacheClass;
import com.credithc.cache.impl.defau1t.description.CacheMethod;
import com.credithc.cache.impl.defau1t.description.CacheMethodDescription;
import com.credithc.cache.impl.defau1t.service.CacheService;

/**
 * 方法调用处理器类。
 * 
 * @author sai.zhang
 */
public class InvocationProcessor {
	protected Log logger = LogFactory.getLog(getClass());
	
	/**
	 * 缓存服务
	 */
	private CacheService cacheService;

	public InvocationProcessor(CacheService cacheService) {
		this.cacheService = cacheService;
	}
	
	protected String getMethodName(Method method) {
		return method.getName();
	}

	private void afterCall(Method method, Object[] oArgs, Object retObj, CacheMethod cacheMethod) {
		CacheMethodDescription methodDesc = cacheMethod.getDescription();

		if (methodDesc.getCacheEvictionDesc() != null) {
			// 驱逐已经缓存对象
			cacheService.evictObjects(cacheMethod, oArgs);
			if (logger.isDebugEnabled()) {
				logger.debug("Evict objects, class=[" + method.getDeclaringClass().getName() + "], method=["
						+ getMethodName(method) + "].");
			}
		}

		if (methodDesc.isCacheFlag()) {
			// 访问缓存对象
			cacheService.cachedObject(cacheMethod, oArgs, retObj);
			if (logger.isDebugEnabled()) {
				String methodName = method.toGenericString();
				methodName = methodName.substring(methodName.lastIndexOf(".") + 1);
				logger.debug("Cache object, class=[" + method.getDeclaringClass().getName() + "], method=["
						+ getMethodName(method) + "].");
			}
		}
	}

	public Object invoke(CacheMethodInvocation invoc) throws Throwable {
		final Method method = invoc.getMethod();
		final Object[] oArgs = invoc.getArgs();
		CacheClass cacheClass = invoc.getCacheClass();

		final CacheMethod cacheMethod = cacheClass.getCacheMethod(method);

		if (cacheMethod != null) {
			final CacheMethodDescription methodDesc = cacheMethod.getDescription();

			if (methodDesc.isCacheFlag()) {
				// 访问缓存对象
				try {
					Object retObj = cacheService.getCachedObject(cacheMethod, oArgs);
					if (logger.isDebugEnabled()) {
						logger.debug("Hit cache, class=[" + method.getDeclaringClass().getName() + "], method=["
								+ getMethodName(method) + "].");
					}

					return retObj;
				} catch (NotFoundCachedException e) {
				}
			}

			final Object retObj = invoc.proceed();

			if (HookExecutor.isHook()) {
				// 事务启动，需要挂钩执行
				HookExecutor.addHandler(new HookHandler() {
					public void execute() {
						if (logger.isDebugEnabled()) {
							logger.debug("Execute cache operations after call in hook.");
						}

						afterCall(method, oArgs, retObj, cacheMethod);
					}

				});
			} else {
				afterCall(method, oArgs, retObj, cacheMethod);
			}

			return retObj;
		} else {
			return invoc.proceed();
		}
	}

	public CacheService getCacheService() {
		return cacheService;
	}

	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}
}
