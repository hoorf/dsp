package com.github.ruifengho.tx.service;

import com.github.ruifengho.aop.DspTxTransactionAopInfo;

public interface TxServiceFactory {

	TransactionService selectService(String groupId, DspTxTransactionAopInfo local);

}
