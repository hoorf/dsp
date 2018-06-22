package com.github.ruifengho.tx.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.DspTxTransactionAopInfo;
import com.github.ruifengho.modal.TxGroup;
import com.github.ruifengho.modal.TxGroupManager;
import com.github.ruifengho.tx.service.TransactionService;
import com.github.ruifengho.tx.service.TxManagerService;
import com.github.ruifengho.util.RandomUtils;

@Service("txStartTransactionService")
public class TxStartTransactionServiceImpl implements TransactionService {

	@Resource
	private TxManagerService txManagerService;

	@Override
	public Object execute(ProceedingJoinPoint point, String groupId, DspTxTransactionAopInfo info) throws Throwable {

		groupId = StringUtils.isEmpty(groupId) ? RandomUtils.randomUUID() : groupId;

		TxGroup txGroup = TxGroupManager.getInstance().createTxGroup(groupId);

		txManagerService.createTransactionGroup(groupId);
		int state = 0;
		try {
			Object obj = point.proceed();
			state = 1;
			return obj;
		} catch (Exception e) {
			state = rollbackException(e, info);
		} finally {

			int rs = txManagerService.closeTransactionGroup(groupId, state);

		}
		return null;
	}

	private int rollbackException(Throwable e, DspTxTransactionAopInfo info) {
		if (RuntimeException.class.isAssignableFrom(e.getClass())) {
			return 0;
		}
		if (Error.class.isAssignableFrom(e.getClass())) {
			return 0;
		}

		for (Class<? extends Throwable> clazz : info.getAnnotation().rollbackFor()) {
			if (clazz.isAssignableFrom(e.getClass())) {
				return 0;
			}
		}
		for (Class<? extends Throwable> clazz : info.getAnnotation().noRollbackFor()) {
			if (clazz.isAssignableFrom(e.getClass())) {
				return 1;
			}
		}
		return 1;
	}

}
