package com.github.ruifengho.netty;

import io.netty.channel.ChannelHandlerContext;

public interface NettyControlService {

	void restart();

	void process(ChannelHandlerContext ctx, String json);

}