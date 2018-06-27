package com.github.ruifengho.task;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Task {

	private String taskId;

	private volatile boolean isExecuted = false;

	private volatile boolean isAwait = false;

	private volatile boolean isNotify = false;

	private volatile boolean isRunning = false;

	private TaskRunner execute;

	private Object resultObj;

	private int state = 0;

	private Lock lock = new ReentrantLock();

	private Condition condition = lock.newCondition();

	public void setRunner(TaskRunner execute) {
		this.execute = execute;
	}

	public void signalTask(TaskRunner runner) {

		lock.lock();
		try {
			this.execute = runner;
			this.isExecuted = false;
			condition.signal();
			isNotify = true;
		} finally {
			lock.unlock();
		}
	}

	public void runAndWait() throws Throwable {
		lock.lock();
		isAwait = true;
		isNotify = false;
		if (execute != null && isExecuted == false) {
			isAwait = false;
			isRunning = true;
			try {
				resultObj = execute.run();
			} catch (Throwable e) {
				resultObj = e;
			}
			isRunning = false;
			isExecuted = true;
			execute = null;
		}
		lock.unlock();
		condition.await();
		isNotify = true;
	}

	public void signalTask() {
		lock.lock();
		condition.signal();
		lock.unlock();
	}

	public boolean isRunning() {
		return isRunning;
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

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public Object getResultObj() {
		return resultObj;
	}

	public void setState(int state) {
		this.state = state;
	}

}
