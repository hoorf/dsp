package com.github.ruifengho.task;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task {

	private String key;

	// 是否有执行任务
	private boolean hasExecute = false;

	private boolean isAwait = false;

	private boolean isNotify = false;

	private TaskRunner execute;

	private Object resultObj;

	private int state = 0;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	public synchronized Object excute(TaskRunner runner) {
		execute = runner;
		hasExecute = true;

		excuteSignalTask();

		while (execute != null && !Thread.currentThread().isInterrupted()) {
			taskSleep();
		}

		return resultObj;

	}

	public void excuteSignalTask() {
		while (!isAwait && !Thread.currentThread().isInterrupted()) {
			taskSleep();
		}
		try {
			lock.lock();
			condition.signal();
		} finally {
			lock.unlock();
		}

	}

	public void signalTask() {
		signalTask(null);
	}

	public void signalTask(TaskRunner runner) {
		// 有执行任务，等待它完成再执行
		while (hasExecute && !Thread.currentThread().isInterrupted()) {
			taskSleep();
		}
		// 当前正在执行的，不往下执行
		while (!isAwait && !Thread.currentThread().isInterrupted()) {
			taskSleep();
		}
		// 当前task没执行
		try {

			lock.lock();
			isNotify = true;
			if (runner != null) {

				try {
					runner.run();
				} catch (Throwable e) {
				}
			}
			condition.signal();
		} finally {
			lock.unlock();
		}

	}

	private void waitTask() throws Throwable {
		condition.await();
		if (hasExecute) {
			try {
				resultObj = execute.run();
			} catch (Throwable e) {
				resultObj = e;
			}
			hasExecute = false;
			execute = null;
			waitTask();
		}
	}

	/**
	 * 执行后等待
	 */
	public void awaitTask() {
		try {
			lock.lock();
			isAwait = true;
			try {
				waitTask();
			} catch (Throwable e) {
			}
		} finally {
			lock.unlock();
		}

	}

	private void taskSleep() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isHasExecute() {
		return hasExecute;
	}

	public boolean isAwait() {
		return isAwait;
	}

	public boolean isNotify() {
		return isNotify;
	}

	public int getState() {
		return state;
	}

}
