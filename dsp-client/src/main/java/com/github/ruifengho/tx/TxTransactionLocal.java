package com.github.ruifengho.tx;

public class TxTransactionLocal {

	private final static ThreadLocal<TxTransactionLocal> current = new InheritableThreadLocal<>();

	private String groupId;

	private boolean hasStart = false;

	private boolean readOnly = false;

	public static TxTransactionLocal current() {
		return current.get();
	}

	public static void current(TxTransactionLocal local) {
		current.set(local);
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public boolean isHasStart() {
		return hasStart;
	}

	public void setHasStart(boolean hasStart) {
		this.hasStart = hasStart;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

}
