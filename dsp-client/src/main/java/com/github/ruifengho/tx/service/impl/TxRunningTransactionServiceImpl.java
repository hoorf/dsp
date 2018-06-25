package com.github.ruifengho.tx.service.impl;

import javax.annotation.Resource;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.ruifengho.aop.DspTxTransactionAopInfo;
import com.github.ruifengho.modal.TxGroup;
import com.github.ruifengho.modal.TxGroupManager;
import com.github.ruifengho.modal.TxTask;
import com.github.ruifengho.task.TaskRunner;
import com.github.ruifengho.tx.service.TransactionService;
import com.github.ruifengho.tx.service.TxManagerService;
import com.github.ruifengho.util.RandomUtils;

@Service("txRunningTransactionService")
public class TxRunningTransactionServiceImpl implements TransactionService {

	private static final Logger logger = LoggerFactory.getLogger(TxRunningTransactionServiceImpl.class);

	@Resource
	private TxManagerService txManagerService;

	@Override
	public Object execute(ProceedingJoinPoint point, String groupId, DspTxTransactionAopInfo info) throws Throwable {

		TxGroup txGroup = TxGroupManager.getInstance().createTxGroup(groupId);
		String taskId = RandomUtils.randomUUID();
		TxTask txTask = txGroup.createTxTask(taskId);

		txTask.setRunner(new TaskRunner() {
			@Override
			public Object run(Object... objects) throws Throwable {
				Object obj = point.proceed();
				return obj;
			}
		});
		int state = 0;
		try {
			txTask.waitTask();
			state = 1;
			return txTask.getResultObj();
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
