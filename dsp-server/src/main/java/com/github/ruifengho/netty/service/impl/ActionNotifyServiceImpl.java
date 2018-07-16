package com.github.ruifengho.netty.service.impl;

import java.util.Collection;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.netty.service.ActionService;
import com.github.ruifengho.netty.utils.ChannelManager;
import com.github.ruifengho.tx.TxManagerPool;
import com.github.ruifengho.util.SocketUtils;

import io.netty.channel.ChannelHandlerContext;

@Service(DspConstants.ACTION_NOTIFY)
public class ActionNotifyServiceImpl implements ActionService {

	private static final Logger log = LoggerFactory.getLogger(ActionNotifyServiceImpl.class);

	@Override
	public String execute(ChannelHandlerContext ctx, DspAction action) {
		log.debug("开始通知全局事务处理");
		Collection<ChannelHandlerContext> groups = ChannelManager.getInstance().getGroup(action.getGroupId());
		if (CollectionUtils.isNotEmpty(groups)) {
			action.setType(DspConstants.MSG_TYPE_SERVER);
			action.setAction(DspConstants.ACTION_NOTIFY);

			TxManagerPool.setState(action.getGroupId(), action.getState());

			groups.forEach(ct -> {
				log.debug("通知[{}]", ct.channel().remoteAddress().toString());
				SocketUtils.sendMsg(ct, action.toString());
			});
		}
		return String.format("%s server notify all for group[%s] ", ctx.channel().remoteAddress().toString(),
				action.getGroupId());
	}

}
