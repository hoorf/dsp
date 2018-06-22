package com.github.ruifengho.modal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TxGroup {

	private String groupId;

	private static Map<String, TxTask> map = new ConcurrentHashMap<>();

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	
	
	
	
}
