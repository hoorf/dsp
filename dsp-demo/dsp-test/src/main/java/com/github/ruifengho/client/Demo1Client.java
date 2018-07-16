package com.github.ruifengho.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("demo1")
public interface Demo1Client {
	@RequestMapping("/demo1/client")
	public void excute();
}
