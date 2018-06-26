package com.github.ruifengho.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.ruifengho.interceptor.DspRestTemplateInterceptor;

import feign.RequestInterceptor;

@Configuration
public class DspClientSpringCloudConfig {

	@Bean
	public RequestInterceptor requestInterceptor() {
		return new DspRestTemplateInterceptor();

	}
}
