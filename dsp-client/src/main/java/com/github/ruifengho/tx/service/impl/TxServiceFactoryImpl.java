package com.github.ruifengho.tx.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.DspTxTransactionAopInfo;
import com.github.ruifengho.tx.service.TransactionService;
import com.github.ruifengho.tx.service.TxServiceFactory;

@Service
public class TxServiceFactoryImpl implements TxServiceFactory {

	@Resource(name = "txStartTransactionService")
	private TransactionService start;

	@Resource(name = "txRunningTransactionService")
	private TransactionService running;

	@Resource(name = "txDefaultTransactionService")
	private TransactionService defaultService;

	@Override
	public TransactionService selectService(String groupId, DspTxTransactionAopInfo info) {
		TransactionService service = defaultService;
		if (info.getAnnotation().isStart() && StringUtils.isEmpty(groupId)) {
			service = start;
		}
		if (!StringUtils.isEmpty(groupId)) {
			service = running;
		}
		return service;
	}

}
