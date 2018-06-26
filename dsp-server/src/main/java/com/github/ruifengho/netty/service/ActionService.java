package com.github.ruifengho.netty.service;

import com.github.ruifengho.modal.DspAction;

import io.netty.channel.ChannelHandlerContext;

public interface ActionService {

	String execute(ChannelHandlerContext ctx, DspAction action);

}
