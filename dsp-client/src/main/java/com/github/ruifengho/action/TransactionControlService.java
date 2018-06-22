package com.github.ruifengho.action;

import io.netty.channel.ChannelHandlerContext;

public interface TransactionControlService {
	/**
	 * 
	 * @param ctx
	 * @param resObj
	 * @param json
	 *            收到的json
	 */
	void notifyTransactionMsg(ChannelHandlerContext ctx, String json);
}
