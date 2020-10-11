package com.tr.utility.support.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class CommonJoinPointConfig {

    @Pointcut("@annotation(com.tr.utility.support.annotation.ErrorLogger)")
    public void errorLoggerAnnotation(){}
}
