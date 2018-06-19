package com.github.ruifengho.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConfigReader {

	@Value("${dsp.transaction.netty.delaytime}")
	private int transactionNettyDelaytime;

	@Value("${dsp.transaction.netty.port}")
	private int port;
	@Value("${dsp.transaction.netty.heart}")
	private int heart;
	@Value("${dsp.transaction.netty.host}")
	private String host;

	public int getTransactionNettyDelaytime() {
		return transactionNettyDelaytime;
	}

	public int getPort() {
		return port;
	}

	public int getHeart() {
		return heart;
	}

	public String getHost() {
		return host;
	}

}
