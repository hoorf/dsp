package com.github.ruifengho.netty.service.impl;

import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.netty.service.ActionService;
import com.github.ruifengho.tx.TxManagerPool;

import io.netty.channel.ChannelHandlerContext;

@Service(DspConstants.ACTION_CHECK_TX_GROUP)
public class ActionCheckServiceImpl implements ActionService {

	@Override
	public String execute(ChannelHandlerContext ctx, DspAction action) {
		action.setState(TxManagerPool.getState(action.getGroupId()));
		return TxManagerPool.getState(action.getGroupId()) + "";
	}

}
