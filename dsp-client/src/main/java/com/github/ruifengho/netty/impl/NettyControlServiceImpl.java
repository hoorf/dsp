package com.github.ruifengho.netty.impl;

import java.sql.Connection;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.modal.TxGroup;
import com.github.ruifengho.modal.TxTask;
import com.github.ruifengho.netty.NettyClientService;
import com.github.ruifengho.netty.NettyControlService;
import com.github.ruifengho.utils.ConnectionManager;
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
		DspAction dspAction = DspAction.parse(json);
		if (DspConstants.MSG_TYPE_SERVER.equals(dspAction.getType())) {

			switch (dspAction.getAction()) {
			case DspConstants.ACTION_UPLOAD_CLIENT_MSG: {
				TxTask txTask = TxGroup.getTxTask(dspAction.getGroupId());
				if (txTask != null) {
					if (txTask.getTaskId().equals(dspAction.getParams().getString("taskId"))) {
						txTask.setState(dspAction.getState());
						// txTask.signalTask();
					}
				}
				break;
			}
			case DspConstants.ACTION_CHECK_TX_GROUP: {
				TxTask txTask = TxGroup.getTxTask(dspAction.getGroupId());
				if (txTask != null) {
					Connection connection = ConnectionManager.get(txTask);
					if (connection != null) {
						try {
							if (dspAction.getState() == DspConstants.STATE_ROLLBACK) {
								connection.rollback();
							}
						} catch (Exception e) {
							e.printStackTrace();
							log.debug("rollback connection for group【{}】", dspAction.getGroupId());
						}
					}

				}
				break;
			}
			case DspConstants.ACTION_NOTIFY: {
				TxTask txTask = TxGroup.getTxTask(dspAction.getGroupId());
				if (txTask != null) {
					Connection connection = ConnectionManager.get(txTask);
					if (connection != null) {
						try {
							if (dspAction.getState() == DspConstants.STATE_COMMIT) {
								connection.commit();
							} else {
								connection.rollback();
							}
						} catch (Exception e) {
							e.printStackTrace();
							log.debug("rollback connection for group【{}】", dspAction.getGroupId());
						}
					}

				}
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
