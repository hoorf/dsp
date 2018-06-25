package com.github.ruifengho.netty.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.modal.TxGroup;
import com.github.ruifengho.modal.TxGroupManager;
import com.github.ruifengho.netty.NettyClientService;
import com.github.ruifengho.netty.NettyControlService;
import com.github.ruifengho.utils.SocketManager;

import io.netty.channel.ChannelHandlerContext;

@Service
public class NettyControlServiceImpl implements NettyControlService {

	@Resource
	private NettyClientService nettyClientService;

	@Override
	public void restart() {
		nettyClientService.disconnect();

		try {
			Thread.sleep(1000 * 3);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		nettyClientService.connect();

	}

	public void process(ChannelHandlerContext ctx, String json) {
		DspAction dspAction = JSONObject.parseObject(json, DspAction.class);
		if (DspConstants.MSG_TYPE_SERVER.equals(dspAction.getType())) {

			switch (dspAction.getAction()) {
			case DspConstants.ACTION_NOTIFY: {
				TxGroup txGroup = TxGroupManager.getInstance().getTxGroup(dspAction.getGroupId());
				txGroup.notifyAllTask();
				break;
			}
			default:
				break;
			}
		} else {
			if (DspConstants.ACTION_HEART.equals(dspAction.getAction())) {
				SocketManager.getInstance().setConnected(true);
				// 同步delay
			}

		}

	}

}
