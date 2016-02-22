package com.credithc.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

public class ReflectUtil {
    
    public static Class<?> getByName(String fullClassName){
        try {
            return Class.forName(fullClassName);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
    
    public static Object newInstance(Class<?> clazz){
        try {
            return clazz.newInstance();
        } catch (Exception e){
            return null;
        }
    }
    
    public static Object newInstance(String fullClassName){
            Class<?> clazz = getByName(fullClassName);
            return newInstance(clazz);
    }
    
    public static Method getMethod(Class<?> clazz, String methodName, Class<?> ... methodParaTypes){
        try {
            Method mtd = null;
            if(clazz != null){
                mtd = clazz.getMethod(methodName, methodParaTypes);
            }
            return mtd;
        } catch (Exception e) {
            return null;
        } 
    }
    
    public static Class<?> [] getAllInterfaces(Class<?> clazz){
        return clazz.getInterfaces();
    }
    
    public static <T, V> Class<T> reflectParameterizedType(Class<V> clazz){
        return reflectParameterizedType(clazz, 0);
    }

    @SuppressWarnings("unchecked")
    public static <T, V> Class<T> reflectParameterizedType(Class<V> clazz, int paramIndex) {
        if (clazz != null) {
            Type genType = clazz.getGenericSuperclass();

            if (genType instanceof ParameterizedType) {
                Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
                if (paramIndex >= 0 &&paramIndex < params.length) {
                    if (params[paramIndex] instanceof Class) {
                        return (Class<T>) params[paramIndex];
                    }
                }
            }
        }

        return null;
    }
    
    public static void map2Bean(Map<String, Object> source, Object target) 
    		throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
    	
    	Class<? extends Object> cls = target.getClass();
    	for(String key : source.keySet()){
    		Field field = cls.getDeclaredField(key);
    		if(field != null){
    			field.setAccessible(true);
    			field.set(target, source.get(key));
    		}
    	}
    	
    }

}
