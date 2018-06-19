package com.github.ruifengho.config;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;

@Component
public class ConfigLoader {

	private static final Logger log = LoggerFactory.getLogger(ConfigLoader.class);

	@Value("${dsp.server.address}")
	private String serverAddress;

	public void load() {
		try {
			CloseableHttpClient httpClient = HttpClients.custom().build();
			HttpUriRequest request = new HttpGet(serverAddress + "/getServer");
			CloseableHttpResponse res = httpClient.execute(request);
			String json = EntityUtils.toString(res.getEntity());
			DspServerConfig parseObject = JSONObject.parseObject(json, DspServerConfig.class);
			Constants.config = parseObject;
		} catch (Exception e) {
			log.error("调度服务器连接失败");
			e.printStackTrace();
			throw new IllegalArgumentException("调度服务器连接失败");
		}

	}

}
