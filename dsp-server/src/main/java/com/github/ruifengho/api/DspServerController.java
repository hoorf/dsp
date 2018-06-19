package com.github.ruifengho.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.github.ruifengho.config.ConfigReader;
import com.github.ruifengho.config.DspServerConfig;

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
}
