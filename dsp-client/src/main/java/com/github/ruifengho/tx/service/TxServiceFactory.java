package com.github.ruifengho.tx.service;

import com.github.ruifengho.tx.TxTransactionLocal;

public interface TxServiceFactory {

	TransactionService selectService(String groupId, TxTransactionLocal local);

}
