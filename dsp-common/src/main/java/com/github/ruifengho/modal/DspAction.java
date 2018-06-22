package com.github.ruifengho.modal;

import com.alibaba.fastjson.JSONObject;

public class DspAction {

	private String type;

	private String action;
	private String groupId;
	private JSONObject params = new JSONObject();

	public DspAction() {
		super();
	}

	public DspAction(String type, String action, String groupId) {
		super();
		this.type = type;
		this.action = action;
		this.groupId = groupId;
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

	public JSONObject getParams() {
		return params;
	}

	public void setParams(JSONObject params) {
		this.params = params;
	}

	@Override
	public String toString() {
		this.params.put("type", type);
		this.params.put("action", action);
		this.params.put("groupId", groupId);
		return params.toJSONString();
	}

}
