package com.github.ruifengho.tx.service;

import org.aspectj.lang.ProceedingJoinPoint;

import com.github.ruifengho.tx.TxTransactionLocal;

public interface TransactionService {

	Object execute(ProceedingJoinPoint point, String groupId, TxTransactionLocal local) throws Throwable;

}
