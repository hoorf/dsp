package com.github.ruifengho.netty.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.netty.service.ActionService;
import com.github.ruifengho.netty.utils.ChannelManager;
import com.github.ruifengho.tx.TxManagerPool;
import com.github.ruifengho.util.SocketUtils;

import io.netty.channel.ChannelHandlerContext;

@Service(DspConstants.ACTION_CLOSE_TX_GROUP)
public class ActionNotifyServiceImpl implements ActionService {

	@Override
	public String execute(ChannelHandlerContext ctx, DspAction action) {

		List<ChannelHandlerContext> groups = ChannelManager.getInstance().getGroup(action.getGroupId());
		if (CollectionUtils.isNotEmpty(groups)) {
			action.setType(DspConstants.MSG_TYPE_SERVER);
			action.setAction(DspConstants.ACTION_NOTIFY);

			TxManagerPool.setState(action.getGroupId(), action.getState());

			groups.forEach(ct -> {
				SocketUtils.sendMsg(ct, action.toString());
			});
		}
		return String.format("%s server notify all for group[%s] ", ctx.channel().remoteAddress().toString(),
				action.getGroupId());
	}

}
