package com.github.ruifengho.netty.handler;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.netty.service.ActionService;
import com.github.ruifengho.netty.service.NettyService;
import com.github.ruifengho.util.SocketUtils;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class DspServerHandler extends ChannelInboundHandlerAdapter {

	private static final Logger log = LoggerFactory.getLogger(DspServerHandler.class);

	private ExecutorService threadPool;

	private NettyService nettyService;

	public DspServerHandler(ExecutorService threadPool, NettyService nettyService) {
		super();
		this.threadPool = threadPool;
		this.nettyService = nettyService;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String json = SocketUtils.getJson(msg);
		log.debug("request:{}", json);
		threadPool.execute(() -> {
			doService(ctx, json);
		});

	}

	private void doService(ChannelHandlerContext ctx, String json) {

		DspAction dspAction = JSONObject.parseObject(json, DspAction.class);

		ActionService service = nettyService.getService(dspAction.getAction());
		String address = ctx.channel().remoteAddress().toString();

		String result = service.execute(address, dspAction.getKey(), dspAction.getParams());

		JSONObject jobject = new JSONObject();
		jobject.put("action", dspAction.getAction());
		jobject.put("response", result);

		SocketUtils.sendMsg(ctx, jobject.toJSONString());

	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		// TODO Auto-generated method stub
		super.userEventTriggered(ctx, evt);
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// TODO Auto-generated method stub
		super.exceptionCaught(ctx, cause);
	}

}
