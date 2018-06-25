package com.github.ruifengho.tx.service.impl;

import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.tx.service.TxManagerService;
import com.github.ruifengho.utils.SocketManager;

@Service
public class TxManagerServiceImpl implements TxManagerService {

	public void createTransactionGroup(String groupId) {
		DspAction dspAction = new DspAction(DspConstants.MSG_TYPE_CLIENT, DspConstants.ACTION_CREATE_TX_GROUP, groupId);
		SocketManager.getInstance().sendMsg(dspAction.toString());
	}

	@Override
	public int closeTransactionGroup(String groupId, int state) {
		DspAction dspAction = new DspAction(DspConstants.MSG_TYPE_CLIENT, DspConstants.ACTION_CLOSE_TX_GROUP, groupId);
		dspAction.getParams().put("state", state);
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
		dspAction.getParams().put("state", state);
		try {
			SocketManager.getInstance().sendMsg(dspAction.toString());
		} catch (Exception e) {
			return 0;
		}

		return 0;

	}

}
