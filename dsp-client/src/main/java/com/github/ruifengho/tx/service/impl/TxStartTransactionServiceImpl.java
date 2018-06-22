package com.github.ruifengho.tx.service.impl;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.DspTxTransactionAopInfo;
import com.github.ruifengho.modal.TxGroup;
import com.github.ruifengho.modal.TxGroupManager;
import com.github.ruifengho.modal.TxTask;
import com.github.ruifengho.tx.service.TransactionService;
import com.github.ruifengho.tx.service.TxManagerService;
import com.github.ruifengho.util.RandomUtils;

@Service("txStartTransactionService")
public class TxStartTransactionServiceImpl implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TxStartTransactionServiceImpl.class);

	@Resource
	private TxManagerService txManagerService;

	@Override
	public Object execute(ProceedingJoinPoint point, String groupId, DspTxTransactionAopInfo info) throws Throwable {
		logger.debug("--->begin start transaction");
		groupId = StringUtils.isEmpty(groupId) ? RandomUtils.randomUUID() : groupId;

		TxGroup txGroup = TxGroupManager.getInstance().createTxGroup(groupId);
		String taskId = RandomUtils.randomUUID();
		txGroup.createTxTask(taskId);

		txManagerService.createTransactionGroup(groupId);
		int state = 0;
		try {
			Object obj = point.proceed();
			state = 1;
			return obj;
		} catch (Exception e) {
			state = rollbackException(e, info);
			throw e;
		} finally {
			int rs = txManagerService.closeTransactionGroup(groupId, state);
			TxTask waitTxTask = txGroup.getTxTask(taskId);
			boolean commit = false;
			if (waitTxTask != null) {
				waitTxTask.signalTask();
				while (waitTxTask.isRunning()) {
					try {
						Thread.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				if (rs == 1) {
					commit = true;
				}

			}
			logger.debug("<---end start transaction");
			logger.debug("start transaction over, res -> groupId:" + groupId + ", now state:"
					+ (commit ? "commit" : "rollback"));

		}
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
