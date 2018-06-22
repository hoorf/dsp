package com.github.ruifengho.modal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.ruifengho.task.Task;

public class TxGroup extends Task {

	private String groupId;

	private static Map<String, TxTask> map = new ConcurrentHashMap<>();

	public TxTask createTxTask(String taskId) {
		TxTask task = new TxTask();
		task.setTaskId(taskId);
		task.setGroupId(groupId);
		putTxTask(task);
		return task;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public TxTask getTxTask(String taskId) {
		return map.get(taskId);
	}

	public void putTxTask(TxTask task) {
		map.put(task.getTaskId(), task);
	}

	public boolean hasRunningTxTask() {
		long count = map.entrySet().stream().filter(x -> x.getValue().isRunning()).count();
		if (count > 0) {
			return true;
		} else {
			return false;
		}
	}

}
