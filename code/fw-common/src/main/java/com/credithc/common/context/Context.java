package com.credithc.common.context;

import java.util.HashMap;
import java.util.Map;

public class Context {
	
	private static final ThreadLocal<Map<String, Object>> threadLocal = new InheritableThreadLocal<Map<String, Object>>();
	
	public static void initContext(){
		threadLocal.set(new HashMap<String, Object>());
	}
	
	public static void put(String key, Object value){
		threadLocal.get().put(key, value);
	}
	
	public static Object get(String key){
		return threadLocal.get().get(key);
	}
	
	public static void destoryContext(){
		threadLocal.remove();
	}

}
