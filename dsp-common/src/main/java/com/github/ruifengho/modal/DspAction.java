package com.github.ruifengho.modal;

import com.alibaba.fastjson.JSONObject;

public class DspAction {

	private String type;

	private String action;
	private String groupId;
	private Integer state;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	@Override
	public String toString() {
		this.params.put("type", type);
		this.params.put("action", action);
		this.params.put("groupId", groupId);
		this.params.put("state", state);
		return params.toJSONString();
	}

	public static DspAction parse(String json) {
		DspAction action = new DspAction();
		JSONObject parseObject = JSONObject.parseObject(json);
		action.setState(parseObject.getInteger("state"));
		parseObject.remove("state");
		action.setType(parseObject.getString("type"));
		parseObject.remove("type");
		action.setAction(parseObject.getString("action"));
		parseObject.remove("action");
		action.setGroupId(parseObject.getString("groupId"));
		parseObject.remove("groupId");
		action.setParams(parseObject);
		return action;

	}

}
