package com.github.ruifengho.netty.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.netty.service.ActionService;
import com.github.ruifengho.netty.utils.ChannelManager;
import com.github.ruifengho.util.SocketUtils;

import io.netty.channel.ChannelHandlerContext;

@Service(DspConstants.ACTION_NOTIFY)
public class ActionNotifyServiceImpl implements ActionService {

	@Override
	public String execute(String channelAddress, String groupId, String params) {

		JSONObject json = JSONObject.parseObject(params);
		List<ChannelHandlerContext> groups = ChannelManager.getInstance().getGroup(groupId);
		if (CollectionUtils.isNotEmpty(groups)) {
			DspAction dsp = new DspAction(DspConstants.MSG_TYPE_SERVER, DspConstants.ACTION_NOTIFY, groupId);
			dsp.setParams(json);
			groups.forEach(ctx -> {
				SocketUtils.sendMsg(ctx, dsp.toString());
			});
		}
		return String.format("%s server notify all for group[%s] ", channelAddress, groupId);
	}

}
