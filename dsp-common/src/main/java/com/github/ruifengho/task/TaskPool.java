package com.github.ruifengho.task;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TaskPool {

	private static TaskPool instance = null;

	private static Map<String, Task> map = new ConcurrentHashMap<>();

	private TaskPool() {
	}

	public static TaskPool getInstance() {

		if (instance == null) {
			synchronized (TaskPool.class) {
				if (instance == null) {
					instance = new TaskPool();
				}
			}
		}
		return instance;
	}

	public static Task createTask(String key) {
		Task task = new Task();
		task.setKey(key);
		map.put(key, task);
		return task;
	}

	public static void removeTask(String key) {
		if (map.containsKey(key)) {
			map.remove(key);
		}
	}

	public static boolean hasTask(String key) {
		return map.containsKey(key);
	}

}
