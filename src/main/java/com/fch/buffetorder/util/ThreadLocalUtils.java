package com.fch.buffetorder.util;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 线程本地工具类
 */
public class ThreadLocalUtils {

    private static final ThreadLocal<Map<String, Object>> RESOURCES = ThreadLocal.withInitial(ConcurrentHashMap::new);

    public static void removeResources() {
        RESOURCES.remove();
    }

    public static Map<String, Object> getResources() {
        return RESOURCES.get();
    }

    public static void put(String key, Object value) {
        Objects.requireNonNull(key, "key cannot be null");
        getResources().put(key, value);
    }

    public static Object get(String key) {
        return Objects.isNull(getResources()) ? null : getResources().get(key);
    }

    public static Object remove(String key) {
        return Objects.isNull(getResources()) ? null : getResources().remove(key);
    }

}
