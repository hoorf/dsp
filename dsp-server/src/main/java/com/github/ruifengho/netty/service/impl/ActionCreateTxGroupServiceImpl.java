package com.github.ruifengho.netty.service.impl;

import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.netty.service.ActionService;

import io.netty.channel.ChannelHandlerContext;

@Service(DspConstants.ACTION_CREATE_TX_GROUP)
public class ActionCreateTxGroupServiceImpl implements ActionService {

	@Override
	public String execute(ChannelHandlerContext ctx, DspAction action) {
		return String.format("create group 【%s】 success", action.getGroupId());
	}

}
