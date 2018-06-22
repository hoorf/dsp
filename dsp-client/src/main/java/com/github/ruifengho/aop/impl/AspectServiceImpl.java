package com.github.ruifengho.aop.impl;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.AspectService;
import com.github.ruifengho.aop.DspTxTransactionAopInfo;
import com.github.ruifengho.tx.service.TransactionService;
import com.github.ruifengho.tx.service.TxServiceFactory;

@Service
public class AspectServiceImpl implements AspectService {

	@Resource
	private TxServiceFactory txServiceFactory;

	@Override
	public Object around(String groupId, ProceedingJoinPoint point) throws Throwable {
		DspTxTransactionAopInfo aopInfo = new DspTxTransactionAopInfo(point);
		TransactionService selectService = txServiceFactory.selectService(groupId, aopInfo);
		return selectService.execute(point, groupId, aopInfo);
	}

}
