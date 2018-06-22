package com.github.ruifengho.action.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.action.IActionService;
import com.github.ruifengho.action.TransactionControlService;
import com.github.ruifengho.modal.DspAction;

import io.netty.channel.ChannelHandlerContext;

@Service
public class TransactionControlServiceImpl implements TransactionControlService {

	@Autowired
	private ApplicationContext spring;

	@Override
	public void notifyTransactionMsg(ChannelHandlerContext ctx, String json) {
		DspAction action = JSONObject.parseObject(json, DspAction.class);
		IActionService service = spring.getBean(action.getAction(), IActionService.class);
		service.execute(json);
	}

}
