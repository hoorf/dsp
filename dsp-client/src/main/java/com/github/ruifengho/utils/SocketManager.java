package com.github.ruifengho.utils;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.util.SocketUtils;

import io.netty.channel.ChannelHandlerContext;

public class SocketManager {

	private ChannelHandlerContext ctx;

	private static SocketManager instance;

	private Boolean isConnected = false;

	private SocketManager() {
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public void setConnected(boolean flag) {
		this.isConnected = flag;
	}

	public Boolean isConnected() {
		return isConnected;
	}

	public void sendMsg(Object obj) {
		SocketUtils.sendMsg(ctx, JSONObject.toJSONString(obj));
	}

	public static SocketManager getInstance() {
		if (instance == null) {
			synchronized (SocketManager.class) {
				if (instance == null) {
					instance = new SocketManager();
				}
			}
		}
		return instance;
	}

}
