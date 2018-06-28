package com.github.ruifengho.modal;

public class TxTaskLocal {
	static final ThreadLocal<TxTask> store = new InheritableThreadLocal<>();

	public static void current(TxTask groupId) {
		store.set(groupId);
	}

	public static TxTask current() {
		return store.get();
	}

}
