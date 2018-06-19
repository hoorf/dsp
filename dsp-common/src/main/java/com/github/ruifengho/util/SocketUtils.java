package com.github.ruifengho.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.ReferenceCountUtil;

public class SocketUtils {

	public static String getJson(Object obj) {
		String str;
		try {
			ByteBuf buf = (ByteBuf) obj;
			byte[] bytes = new byte[buf.readableBytes()];
			buf.readBytes(bytes);
			str = new String(bytes);
		} finally {
			ReferenceCountUtil.release(obj);
		}
		return str;
	}

	public static void sendMsg(ChannelHandlerContext ctx, String msg) {
		ctx.writeAndFlush(Unpooled.buffer().writeBytes(msg.getBytes()));
	}

}
