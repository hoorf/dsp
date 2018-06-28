package com.github.ruifengho.utils;

import java.sql.Connection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.github.ruifengho.modal.TxTask;

public class ConnectionManager {

	private static Map<TxTask, Connection> map = new ConcurrentHashMap<>();

	public static void put(TxTask task, Connection conn) {
		map.put(task, conn);
	}

	public static Connection get(TxTask task) {
		return map.get(task);
	}
}
