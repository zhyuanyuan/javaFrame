package com.credithc.cache.common.api;

import java.util.List;
import java.util.Stack;

/**
 * 挂钩执行器实现类。
 * 
 * @author sai.zhang
 */
public class HookExecutor {
	protected static ThreadLocal<Stack<List<HookHandler>>> handlers = new ThreadLocal<Stack<List<HookHandler>>>();
	
	public static boolean isHook() {
		if ( handlers.get() != null ) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void addHandler(HookHandler handler) {
		Stack<List<HookHandler>> stack = handlers.get();
		List<HookHandler> handlers = stack.peek();
		handlers.add(handler);
	}
}
