package com.fch.buffetorder.aspect;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AfterClearCache {
    String key() default "";

    boolean fromArg() default false;

    int keyIndex() default 0;
}
