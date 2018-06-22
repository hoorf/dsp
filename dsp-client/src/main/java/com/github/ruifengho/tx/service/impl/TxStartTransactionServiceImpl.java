package com.github.ruifengho.tx.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.github.ruifengho.tx.TxTransactionLocal;
import com.github.ruifengho.tx.service.TransactionService;
import com.github.ruifengho.tx.service.TxManagerService;
import com.github.ruifengho.util.RandomUtils;

@Service("txStartTransactionService")
public class TxStartTransactionServiceImpl implements TransactionService {

	@Resource
	private TxManagerService txManagerService;

	@Override
	public Object execute(ProceedingJoinPoint point, String groupId, TxTransactionLocal local) throws Throwable {

		groupId = StringUtils.isEmpty(groupId) ? RandomUtils.randomUUID() : groupId;

		txManagerService.createTransactionGroup(groupId);
		return null;
	}

}
