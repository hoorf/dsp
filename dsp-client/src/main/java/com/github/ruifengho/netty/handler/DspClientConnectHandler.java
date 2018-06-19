package com.github.ruifengho.netty.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.util.SocketUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DspClientConnectHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(DspClientConnectHandler.class);

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		String str = JSON.toJSONString(new DspAction(DspConstants.ACTION_HEART, null, "K"));
		SocketUtils.sendMsg(ctx, str);
	}

}
