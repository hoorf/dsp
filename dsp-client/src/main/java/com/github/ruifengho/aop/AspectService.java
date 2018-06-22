package com.github.ruifengho.aop;

import org.aspectj.lang.ProceedingJoinPoint;

public interface AspectService {
	
	Object around(String groupId, ProceedingJoinPoint point) throws Throwable;

}
