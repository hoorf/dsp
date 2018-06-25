package com.github.ruifengho.tx.service;

public interface TxManagerService {

	/**
	 * 创建调度组
	 * 
	 * @param groupId
	 */
	void createTransactionGroup(String groupId);

	/**
	 * 关闭事务组
	 * 
	 * @param groupId
	 * @param state
	 * @return
	 */
	int closeTransactionGroup(String groupId, int state);

	/**
	 * 通知事务组
	 * 
	 * @param groupId
	 * @param state
	 * @return
	 */
	int notifyTransaction(String groupId, int state);

}
