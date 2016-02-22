package com.credithc.cache.impl.defau1t.service;

import java.lang.reflect.Type;
import java.util.UUID;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.jxpath.JXPathContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.cache.common.api.CacheProvider;
import com.credithc.cache.common.api.NotFoundCachedException;
import com.credithc.cache.common.api.ObjectHolder;
import com.credithc.cache.impl.defau1t.description.CacheChecksumHelper;
import com.credithc.cache.impl.defau1t.description.CacheEvictionDescription;
import com.credithc.cache.impl.defau1t.description.CacheKeyDescription;
import com.credithc.cache.impl.defau1t.description.CacheMethod;
import com.credithc.cache.impl.defau1t.description.CacheMethodDescription;
import com.credithc.cache.impl.defau1t.description.ObjectRef;
import com.credithc.cache.impl.defau1t.description.ReturnDescription;
import com.credithc.cache.impl.defau1t.json.JSONObject;

/**
 * 缺省缓存服务实现类。
 * 
 * @author sai.zhang
 */
public class DefaultCacheServiceImpl implements CacheService {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * 缓存提供者
	 */
	private CacheProvider cacheProvider;

	private String getKey(CacheKeyDescription[] cacheKeyDescs, Object[] args) {
		StringBuffer sb = new StringBuffer();
		boolean flag = false;
		for (CacheKeyDescription ckDesc : cacheKeyDescs) {
			Object arg = args[ckDesc.getArgIdx()];
			if (ckDesc.getJxpathAccessor() != null && arg != null) {
				arg = JXPathContext.newContext(arg).getValue(ckDesc.getJxpathAccessor());
			}

			if (flag) {
				sb.append(",");
			}

			if (ckDesc.getPropertyName() != null) {
				sb.append(ckDesc.getPropertyName());
				sb.append("=");
			}

			Object jsonObj = JSONObject.wrap(arg);
			sb.append(jsonObj.toString());
			flag = true;
		}

		return sb.toString();
	}

	private String getKey(CacheMethod method, Object[] args) {
		CacheKeyDescription[] cacheKeyDescs = method.getDescription().getCacheKeyDescs();
		if (cacheKeyDescs == null || cacheKeyDescs.length == 0) {
			StringBuffer sb = new StringBuffer();
			if (args != null) {
				boolean flag = false;
				for (Object arg : args) {
					if (flag) {
						sb.append(",");
					}

					Object jsonObj = JSONObject.wrap(arg);
					sb.append(jsonObj.toString());
					flag = true;
				}
			}

			return sb.toString();
		}

		return getKey(cacheKeyDescs, args);
	}

	public Object getCachedObject(CacheMethod method, Object[] args)
			throws NotFoundCachedException {
		CacheMethodDescription cmDesc = method.getDescription();
		String entity = cmDesc.getCacheEntity();
		String key = getKey(method, args);

		Type[] expectClasses = new Type[2];
		expectClasses[0] = ObjectRef.class;
		expectClasses[1] = method.getMethod().getGenericReturnType();

		ObjectHolder cachedObjHolder = cacheProvider.getCachedObject(entity, key,
				expectClasses);
		Object cachedObj = cachedObjHolder.getObject();
		if (cachedObj == null) {
			return null;
		}

		if (cachedObj instanceof ObjectRef) {
			ObjectRef objectRef = (ObjectRef) cachedObj;
			ObjectHolder objHolder = cacheProvider.getCachedObject(objectRef.getEntity(),
					objectRef.getKey(), expectClasses);

			if (objHolder.getObjectVersion().equals(objectRef.getObjectVersion()) == false
					|| CacheChecksumHelper.isNeedForceUpdate(method.getDescription().getCahceEntityChecksum(), objHolder.getObjectChecksum())) {
				// 版本不一致或者checksum比较不通过
				cacheProvider.evictObject(entity, key);
				throw new NotFoundCachedException();
			}

			cachedObj = objHolder.getObject();

		}

		return cachedObj;
	}

	private ObjectHolder newObjectHolder(Object obj, CacheMethod method,
			boolean isCachedCheckSum) {
		ObjectHolder objHolder = new ObjectHolder();
		objHolder.setObjectVersion(UUID.randomUUID().toString());
		objHolder.setObject(obj);
		if (isCachedCheckSum) {
			objHolder.setObjectChecksum(method.getDescription().getCahceEntityChecksum());
		}
		return objHolder;
	}

	public void cachedObject(CacheMethod method, Object[] args, Object retObj) {
		try {
			CacheMethodDescription cmDesc = method.getDescription();
			if (cmDesc.isCacheNull() == false && retObj == null) {
				return;
			}

			Object cacheObj = retObj;
			String entity = cmDesc.getCacheEntity();
			String key = getKey(method, args);

			ReturnDescription retDesc = cmDesc.getReturnDesc();
			if (retDesc != null && retObj != null) {
				// 缓存返回对象实体
				String refEntity = retDesc.getEntity();
				boolean flag = false;
				StringBuffer sb = new StringBuffer();
				for (ReturnDescription.KeyItem keyItem : retDesc.getKeys()) {
					Object prop = BeanUtils.getProperty(retObj, keyItem.getPropertyName());

					if (prop != null && keyItem.getJxpathAccessor() != null) {
						prop = JXPathContext.newContext(prop).getValue(
								keyItem.getJxpathAccessor());
					}

					if (flag) {
						sb.append(",");
					}

					sb.append(keyItem.getPropertyName());
					sb.append("=");

					Object jsonObj = JSONObject.wrap(prop);
					sb.append(jsonObj.toString());
					flag = true;
				}

				String refKey = sb.toString();
				ObjectHolder objHolder = newObjectHolder(retObj, method, true);
				cacheProvider.cacheObject(refEntity, refKey, objHolder, retDesc.getTtl());

				if (logger.isDebugEnabled()) {
					logger.debug("Cache the object, entity=[" + refEntity + "], key=["
							+ refKey + "], checksum=["
							+ method.getDescription().getCahceEntityChecksum() + "].");
				}

				if (refEntity.equals(entity) && refKey.equals(key)) {
					// 相同引用
					return;
				}

				ObjectRef objRef = new ObjectRef();
				objRef.setEntity(refEntity);
				objRef.setKey(refKey);
				objRef.setObjectVersion(objHolder.getObjectVersion());
				cacheObj = objRef;
			}

			if (logger.isDebugEnabled()) {
				logger.debug("Cache the object, entity=[" + entity + "], key=[" + key + "].");
			}

			cacheProvider.cacheObject(entity, key, newObjectHolder(cacheObj, method, false),
					cmDesc.getCacheTtl());
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public void evictObjects(CacheMethod method, Object[] args) {
		CacheEvictionDescription cacheEvictionDesc = method.getDescription()
				.getCacheEvictionDesc();
		if (cacheEvictionDesc == null) {
			return;
		}

		for (CacheEvictionDescription.CacheEvictItem ceItem : cacheEvictionDesc
				.getCacheEvictItems()) {
			String key = null;
			if (ceItem.getCacheKeyDesc() != null && ceItem.getCacheKeyDesc().length > 0) {
				key = getKey(ceItem.getCacheKeyDesc(), args);
				ObjectHolder objHolder = cacheProvider.evictObject(ceItem.getEntity(),
						key);
				if (objHolder == null) {
					continue;
				}

				Object cachedObj = objHolder.getObject();
				while (cachedObj != null && cachedObj instanceof ObjectRef) {
					ObjectRef objRef = (ObjectRef) cachedObj;
					objHolder = cacheProvider.evictObject(objRef.getEntity(),
							objRef.getKey());
					if (objHolder == null) {
						break;
					}

					cachedObj = objHolder.getObject();
				}
			} else {
				cacheProvider.evictObjects(ceItem.getEntity());
			}
		}
	}

	public void evictObjects(String cacheEntity) {
		cacheProvider.evictObjects(cacheEntity);
	}

	public CacheProvider getCacheProvider() {
		return cacheProvider;
	}

	public void setCacheProvider(CacheProvider cacheProvider) {
		this.cacheProvider = cacheProvider;
	}

}
