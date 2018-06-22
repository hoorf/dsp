package com.github.ruifengho.modal;

public class DspAction {

	private String type;

	private String action;
	private String groupId;
	private String params;

	public DspAction() {
		super();
	}

	public DspAction(String type, String action, String groupId, String params) {
		super();
		this.type = type;
		this.action = action;
		this.groupId = groupId;
		this.params = params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}

}
