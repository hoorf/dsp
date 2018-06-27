package com.github.ruifengho.modal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TxGroup {

	private static Map<String, TxTask> map = new ConcurrentHashMap<>();
	
	
	

	public static TxTask createTxTask(String groupId) {
		TxTask task = new TxTask();
		task.setGroupId(groupId);
		putTxTask(task);
		return task;
	}

	public void notifyAllTask() {
		for (String taskId : map.keySet()) {
			map.get(taskId).signalTask();
		}
	}

	public static void removeTxTask(String groupId) {
		map.remove(groupId);
	}

	public static TxTask getTxTask(String groupId) {
		return map.get(groupId);
	}

	public static void putTxTask(TxTask task) {
		map.put(task.getGroupId(), task);
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
