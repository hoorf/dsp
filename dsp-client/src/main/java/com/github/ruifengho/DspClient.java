package com.github.ruifengho;

import javax.annotation.Resource;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import com.github.ruifengho.netty.NettyClientService;

@Component
public class DspClient implements InitializingBean {

	@Resource
	private NettyClientService nettyClientService;

	@Override
	public void afterPropertiesSet() throws Exception {
		nettyClientService.connect();
	}

}
