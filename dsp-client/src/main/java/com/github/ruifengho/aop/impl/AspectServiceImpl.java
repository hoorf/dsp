package com.github.ruifengho.aop.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.AspectService;

@Service
public class AspectServiceImpl implements AspectService {

	@Override
	public Object around(String groupId, ProceedingJoinPoint point) throws Throwable {

		return null;
	}

}
