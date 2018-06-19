package com.github.ruifengho.modal;

public class DspAction {

	private String action;
	private String key;
	private String params;

	public DspAction(String action, String key, String params) {
		super();
		this.action = action;
		this.key = key;
		this.params = params;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
