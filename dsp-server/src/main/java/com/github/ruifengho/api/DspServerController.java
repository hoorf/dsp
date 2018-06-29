package com.github.ruifengho.api;

import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.config.ConfigReader;
import com.github.ruifengho.config.DspServerConfig;
import com.github.ruifengho.netty.utils.ChannelManager;

import io.netty.channel.ChannelHandlerContext;

@RestController
@RequestMapping("/dsp")
public class DspServerController {

	@Autowired
	private ConfigReader configReader;

	@RequestMapping("getServer")
	public String getServer() {
		DspServerConfig config = new DspServerConfig();
		config.setHeart(configReader.getHeart());
		config.setPort(configReader.getPort());
		config.setHost(configReader.getHost());
		
		return JSONObject.toJSONString(config);
	}
	@RequestMapping("getConnect")
	public String getConnect() {
		Queue<ChannelHandlerContext> queue = null;
		List<String> collect = queue.stream().map(x->x.channel().remoteAddress().toString()).collect(Collectors.toList());
		return JSONObject.toJSONString(collect);
	}
}
