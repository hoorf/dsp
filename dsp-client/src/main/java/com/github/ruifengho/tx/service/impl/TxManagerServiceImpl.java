package com.github.ruifengho.tx.service.impl;

import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.tx.service.TxManagerService;
import com.github.ruifengho.utils.SocketManager;

@Service
public class TxManagerServiceImpl implements TxManagerService {

	public void createTransactionGroup(String groupId) {
		SocketManager.getInstance()
				.sendMsg(new DspAction(DspConstants.MSG_TYPE_CLIENT, DspConstants.ACTION_CREATE_TX_GROUP, groupId, null));
	}

}
