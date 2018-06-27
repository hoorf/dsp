package com.github.ruifengho.modal;

public class TxTaskLocal {
	static final ThreadLocal<String> store = new InheritableThreadLocal<>();

	public static void current(String groupId) {
		store.set(groupId);
	}

	public static String current() {
		return store.get();
	}

}
