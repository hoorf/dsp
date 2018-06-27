package com.github.ruifengho.modal;

import com.github.ruifengho.task.Task;

public class TxTask extends Task {

	private String groupId;

	public TxTask() {
		super();
	}

	public TxTask(String groupId) {
		super();
		this.groupId = groupId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
