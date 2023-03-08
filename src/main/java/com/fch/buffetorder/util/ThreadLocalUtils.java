package com.fch.buffetorder.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 线程本地工具类
 */
public class ThreadLocalUtils {

    private static ThreadLocal<Map<String, Object>> RESOURCES;

    // 子线程可继承的ThreadLocal
    public static void initInherit() {
        removeResources();
        RESOURCES = new InheritableThreadLocal<>();
        RESOURCES.set(new HashMap<>());
    }

    public static void withInitial() {
        removeResources();
        RESOURCES = ThreadLocal.withInitial(HashMap::new);
    }

    public static void removeResources() {
        if (Objects.nonNull(RESOURCES)) {
            RESOURCES.remove();
        }
    }

    public static Map<String, Object> getResources() {
        return Objects.isNull(RESOURCES) ? null : RESOURCES.get();
    }

    public static void put(String key, Object value) {
        Objects.requireNonNull(key, "key cannot be null");
//        Objects.requireNonNull(getResources(), "RESOURCES have not been inited");
        if (Objects.isNull(getResources())){
            initInherit();
        }
        getResources().put(key, value);
    }

    public static Object get(String key) {
        return Objects.isNull(getResources()) ? null : getResources().get(key);
    }

    public static Object remove(String key) {
        return Objects.isNull(getResources()) ? null : getResources().remove(key);
    }

}
