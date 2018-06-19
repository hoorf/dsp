package com.github.ruifengho.netty.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.github.ruifengho.netty.service.ActionService;
import com.github.ruifengho.netty.service.NettyService;

@Service
public class NettyServiceImpl implements NettyService {

	@Autowired
	private ApplicationContext spring;

	@Override
	public ActionService getService(String action) {
		return spring.getBean(action, ActionService.class);
	}

}
