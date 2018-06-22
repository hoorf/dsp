package com.github.ruifengho.modal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TxGroupManager {

	private static Map<String, TxGroup> map = new ConcurrentHashMap<>();

	private static TxGroupManager instance;

	private TxGroupManager() {
	}

	public static TxGroupManager getInstance() {

		if (instance == null) {
			synchronized (TxGroupManager.class) {
				if (instance == null) {
					instance = new TxGroupManager();
				}

			}
		}
		return instance;
	}

	public TxGroup getTxGroup(String groupId) {
		return map.get(groupId);
	}

	public void putTxGroup(TxGroup group) {
		map.put(group.getGroupId(), group);
	}
}
