package com.fch.buffetorder.aspect;

import java.lang.annotation.*;

/**
 * 被标记的方法会清理redis缓存
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeforeClearCache {
    String key();
}
