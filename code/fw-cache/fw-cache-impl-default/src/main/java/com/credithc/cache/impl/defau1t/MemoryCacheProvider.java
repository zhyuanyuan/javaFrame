package com.credithc.cache.impl.defau1t;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.credithc.cache.common.api.CacheProvider;
import com.credithc.cache.common.api.NotFoundCachedException;
import com.credithc.cache.common.api.ObjectHolder;

/**
 * 基于内存的缓存提供者实现类，只能应用于单机环境。
 * 
 * @author sai.zhang
 */
public class MemoryCacheProvider implements CacheProvider {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	private static class Item {
		private String entity;

		private String key;

		private String objectVersion;
	}

	private SortedMap<Long, List<Item>> itemsByTtl = new TreeMap<Long, List<Item>>();

	private Map<String, Map<String, ObjectHolder>> cache = new ConcurrentHashMap<String, Map<String, ObjectHolder>>();

	private Timer cleanTimer;

	private class CleanTask extends TimerTask {
		@Override
		public void run() {
			clean();
		}
	}

	private synchronized void clean() {
		logger.debug("Start to clean expired cachedObject.");

		long ts = System.currentTimeMillis();
		Map<Long, List<Item>> expiredItems = itemsByTtl.headMap(ts);
		Set<Long> removeKeys = new HashSet<Long>();
		for (Map.Entry<Long, List<Item>> entry : expiredItems.entrySet()) {
			removeKeys.add(entry.getKey());
			for (Item item : entry.getValue()) {
				Map<String, ObjectHolder> map = cache.get(item.entity);
				if (map == null) {
					continue;
				}

				ObjectHolder objHolder = map.get(item.key);
				if (objHolder != null) {
					if (item.objectVersion.equals(objHolder.getObjectVersion())) {
						map.remove(item.key);
						logger.debug("Clean the cachedObject, entity=[" + item.entity + "], key=[" + item.key + "].");
					} else {
						logger.debug("Found newer version entity=[" + item.entity + "], key=[" + item.key + "].");
					}
				}
			}
		}
		
		for ( Long rts : removeKeys ) {
			itemsByTtl.remove(rts);
		}

		logger.debug("Finish clean expired cachedObject.");
	}

	public MemoryCacheProvider() {
		cleanTimer = new Timer("memoryCacheProvider.cleanTask", true);
		cleanTimer.scheduleAtFixedRate(new CleanTask(), 0, 1000);
	}

	public synchronized ObjectHolder getCachedObject(String entity, String key, Type[] expectClasses) throws NotFoundCachedException {
		Map<String, ObjectHolder> map = cache.get(entity);
		if (map == null) {
			throw new NotFoundCachedException();
		}

		if (map.containsKey(key) == false) {
			throw new NotFoundCachedException();
		}

		return map.get(key);
	}

	public synchronized void cacheObject(String entity, String key, ObjectHolder objHolder, int ttl) {
		Map<String, ObjectHolder> map = cache.get(entity);
		if (map == null) {
			map = new HashMap<String, ObjectHolder>();
			cache.put(entity, map);
		}

		map.put(key, objHolder);

		Item item = new Item();
		item.entity = entity;
		item.key = key;
		item.objectVersion = objHolder.getObjectVersion();

		long expiredTime = System.currentTimeMillis() + ttl * 1000;
		List<Item> items = itemsByTtl.get(expiredTime);
		if (items == null) {
			items = new ArrayList<Item>();
			itemsByTtl.put(expiredTime, items);
		}

		items.add(item);
	}

	public synchronized ObjectHolder evictObject(String entity, String key) {
		Map<String, ObjectHolder> map = cache.get(entity);
		if (map == null) {
			return null;
		}

		return map.remove(key);
	}

	public synchronized long evictObjects(String entity) {
		Map<String, ObjectHolder> map = cache.remove(entity);
		if (map != null) {
			return map.size();
		} else {
			return 0;
		}
	}

}
