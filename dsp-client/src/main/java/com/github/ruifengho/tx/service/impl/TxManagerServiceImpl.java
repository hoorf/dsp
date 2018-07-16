package com.github.ruifengho.tx.service.impl;

import javax.annotation.Resource;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.tx.service.TxManagerService;
import com.github.ruifengho.utils.SocketManager;

@Service
public class TxManagerServiceImpl implements TxManagerService {

	@Resource
	private Environment env;

	public void createTransactionGroup(String groupId) {
		DspAction dspAction = new DspAction(DspConstants.MSG_TYPE_CLIENT, DspConstants.ACTION_CREATE_TX_GROUP, groupId);
		dspAction.setState(DspConstants.STATE_COMMIT);
		SocketManager.getInstance().sendMsg(dspAction.toString());
	}

	@Override
	public int closeTransactionGroup(String groupId, String taskId, int state) {
		DspAction dspAction = new DspAction(DspConstants.MSG_TYPE_CLIENT, DspConstants.ACTION_UPLOAD_CLIENT_MSG,
				groupId);
		dspAction.setState(state);
		dspAction.getParams().put("taskId", env.getProperty("spring.application.name") + taskId);
		try {
			SocketManager.getInstance().sendMsg(dspAction.toString());
		} catch (Exception e) {
			return 0;
		}
		return 0;
	}

	@Override
	public int notifyTransaction(String groupId, int state) {
		DspAction dspAction = new DspAction(DspConstants.MSG_TYPE_CLIENT, DspConstants.ACTION_NOTIFY, groupId);
		dspAction.setState(state);
		try {
			SocketManager.getInstance().sendMsg(dspAction.toString());
		} catch (Exception e) {
			return 0;
		}

		return 0;

	}

	@Override
	public int checkTransaction(String groupId) {
		DspAction dspAction = new DspAction(DspConstants.MSG_TYPE_CLIENT, DspConstants.ACTION_CHECK_TX_GROUP, groupId);
		try {
			SocketManager.getInstance().sendMsg(dspAction.toString());
		} catch (Exception e) {
			return 0;
		}

		return 0;
	}

}
