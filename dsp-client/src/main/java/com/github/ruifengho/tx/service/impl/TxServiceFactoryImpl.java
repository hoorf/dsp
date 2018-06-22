package com.github.ruifengho.tx.service.impl;

import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.DspTxTransactionAopInfo;
import com.github.ruifengho.tx.service.TransactionService;
import com.github.ruifengho.tx.service.TxServiceFactory;

@Service
public class TxServiceFactoryImpl implements TxServiceFactory {

	@Override
	public TransactionService selectService(String groupId, DspTxTransactionAopInfo info) {
		// TODO Auto-generated method stub
		return null;
	}

}
