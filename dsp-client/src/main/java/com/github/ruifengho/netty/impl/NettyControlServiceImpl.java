package com.github.ruifengho.netty.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.modal.TxGroup;
import com.github.ruifengho.modal.TxTask;
import com.github.ruifengho.netty.NettyClientService;
import com.github.ruifengho.netty.NettyControlService;
import com.github.ruifengho.utils.SocketManager;

import io.netty.channel.ChannelHandlerContext;

@Service
public class NettyControlServiceImpl implements NettyControlService {

	private static final Logger log = LoggerFactory.getLogger(NettyControlServiceImpl.class);

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
				TxTask txTask = TxGroup.getTxTask(dspAction.getGroupId());
				txTask.signalTask();
				if (txTask.isNotify()) {
					TxGroup.removeTxTask(dspAction.getGroupId());
				}
				break;
			}
			case DspConstants.ACTION_UPLOAD_CLIENT_MSG: {
				log.debug(json);
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
