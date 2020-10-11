package com.tr.utility.support.configuration;

import com.tr.utility.support.annotation.ErrorLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Aspect
@Configuration
public class ErrorCatcherAspectConfiguration {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@AfterThrowing(value = "com.tr.utility.support.aspect.CommonJoinPointConfig.errorLoggerAnnotation()",
			throwing = "exception")
	public void afterThrowing(JoinPoint joinPoint, Exception exception) {
		logger.info("Exception :  " +  exception);
		logger.info("Hey " );
	}

	@Around("execution(* *(..)) && @annotation(com.tr.utility.support.annotation.ErrorLogger)")
	public Object around(ProceedingJoinPoint point) throws  Throwable{
		Method method = getMethod(point);
		ErrorLogger errorLogger = method.getAnnotation(ErrorLogger.class);

		long start = System.currentTimeMillis();
		try {
			return point.proceed();
		}finally {
			long duration = System.currentTimeMillis() - start;
			logger.info("Annotaion given limit : " + errorLogger.limit() + " and level : " + errorLogger.level() );
			logger.error(method.getDeclaringClass().getSimpleName() + "#" + method.getName()+ " DURATION: " +duration);
		}
	}

	private Method getMethod(ProceedingJoinPoint point) throws NoSuchMethodException {
		Method method = MethodSignature.class.cast(point.getSignature()).getMethod();
		if(method.getDeclaringClass().isInterface()){
			method = point.getTarget().getClass().getDeclaredMethod(point.getSignature().getName(), method.getParameterTypes());
		}
		return method;
	}
}