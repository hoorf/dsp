package com.github.ruifengho.netty;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.github.ruifengho.netty.service.NettyServerService;

@Component
public class NettyServerInit implements InitializingBean {

	@Resource
	private NettyServerService nettyService;

	@Override
	public void afterPropertiesSet() throws Exception {
		nettyService.start();
	}

}
