package com.github.ruifengho.modal;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class TxGroupManager {

	private static Map<String, TxGroup> map = new ConcurrentHashMap<>();

	private static TxGroupManager instance;

	private static TxGroup current;

	private TxGroupManager() {
	}

	public TxGroup getCurrent() {
		return current;
	}

	public void setCurrent(TxGroup current) {
		TxGroupManager.current = current;
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

	public TxGroup createTxGroup(String groupId) {
		TxGroup txGroup = new TxGroup();
		txGroup.setGroupId(groupId);
		putTxGroup(txGroup);
		return txGroup;
	}

	public void removeGroup(String groupId) {
		TxGroup txGroup = map.get(groupId);
		if (txGroup != null) {
			while (txGroup.hasRunningTxTask()) {
				try {
					Thread.sleep(1);
				} catch (InterruptedException e) {
				}
			}
			map.remove(groupId);
		}
	}

	public TxGroup getTxGroup(String groupId) {
		return map.get(groupId);
	}

	public void putTxGroup(TxGroup group) {
		map.put(group.getGroupId(), group);
	}
}
