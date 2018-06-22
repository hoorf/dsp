package com.github.ruifengho.aop.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.AspectService;
import com.github.ruifengho.tx.TxTransactionLocal;

@Service
public class AspectServiceImpl implements AspectService {

	@Override
	public Object around(String groupId, ProceedingJoinPoint point) throws Throwable {

		
		TxTransactionLocal current = TxTransactionLocal.current();

		return null;
	}

}
