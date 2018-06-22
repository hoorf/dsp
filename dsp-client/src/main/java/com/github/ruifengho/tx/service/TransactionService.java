package com.github.ruifengho.tx.service;

import org.aspectj.lang.ProceedingJoinPoint;

import com.github.ruifengho.aop.DspTxTransactionAopInfo;

public interface TransactionService {

	Object execute(ProceedingJoinPoint point, String groupId, DspTxTransactionAopInfo info) throws Throwable;

}
