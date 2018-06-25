package com.github.ruifengho.tx.service.impl;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.DspTxTransactionAopInfo;
import com.github.ruifengho.tx.service.TransactionService;

@Service("txDefaultTransactionService")
public class TxDefaultTransactionServiceImpl implements TransactionService {

	@Override
	public Object execute(ProceedingJoinPoint point, String groupId, DspTxTransactionAopInfo info) throws Throwable {
		return point.proceed();
	}

}
