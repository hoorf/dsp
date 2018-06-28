package com.github.ruifengho.netty.handler;

import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.netty.service.ActionService;
import com.github.ruifengho.netty.service.NettyService;
import com.github.ruifengho.netty.utils.ChannelManager;
import com.github.ruifengho.util.SocketUtils;

import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

@Sharable
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
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		super.channelActive(ctx);
		ChannelManager.getInstance().put(ctx);
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		super.channelInactive(ctx);
		ChannelManager.getInstance().remove(ctx);
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

		DspAction dspAction = DspAction.parse(json);

		ActionService service = nettyService.getService(dspAction.getAction());

		ChannelManager.getInstance().group(dspAction.getGroupId(), ctx);

		String result = service.execute(ctx, dspAction);

		DspAction response = new DspAction();
		response.setAction(dspAction.getAction());
		response.getParams().put("response", result);
		response.setType(DspConstants.MSG_TYPE_SERVER);
		response.setState(dspAction.getState());

		SocketUtils.sendMsg(ctx, response.toString());

	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
		if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
			IdleStateEvent event = (IdleStateEvent) evt;
			if (event.state() == IdleState.READER_IDLE) {
				ctx.close();
			}
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
	}

}
