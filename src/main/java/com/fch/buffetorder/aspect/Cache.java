package com.fch.buffetorder.aspect;

import java.lang.annotation.*;

/**
 * 被标记的方法返回值会被redis缓存
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Cache {
    String key() default "";

    long ttl() default 0;

    int keyIndex() default 0;
}
