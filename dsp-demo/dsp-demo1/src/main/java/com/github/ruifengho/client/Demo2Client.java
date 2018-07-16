package com.github.ruifengho.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("demo2")
public interface Demo2Client {

	@RequestMapping("/demo2/client")
	public void excute();

}
