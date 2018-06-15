package com.github.ruifengho;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DspServerApplication {

	private static final Logger log = LoggerFactory.getLogger(DspServerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(DspServerApplication.class, args);
		log.info("事务调度服务器已启动");
	}

}
