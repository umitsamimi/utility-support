package com.tr.utility.support.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorLogger {
    Level level() default Level.INFO;

    int limit() default 0;

    enum Level{
        TRACE, DEBUG, INFO, WARN, ERROR;
    }
}
