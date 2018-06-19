package com.github.ruifengho.config;

public class DspServerConfig {

	private String host;

	private int port;

	private int heart;

	private int delay;

	private int finishedMaxTime;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public int getHeart() {
		return heart;
	}

	public void setHeart(int heart) {
		this.heart = heart;
	}

	public int getDelay() {
		return delay;
	}

	public void setDelay(int delay) {
		this.delay = delay;
	}

	public int getFinishedMaxTime() {
		return finishedMaxTime;
	}

	public void setFinishedMaxTime(int finishedMaxTime) {
		this.finishedMaxTime = finishedMaxTime;
	}

}
