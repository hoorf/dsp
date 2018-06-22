package com.github.ruifengho.tx.service;

public interface TxManagerService {

	void createTransactionGroup(String groupId);

	int closeTransactionGroup(String groupId, int state);

}
