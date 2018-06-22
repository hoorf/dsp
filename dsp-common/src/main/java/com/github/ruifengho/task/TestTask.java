package com.github.ruifengho.task;

import java.util.logging.Logger;

public class TestTask {

	private static final Logger log = java.util.logging.Logger.getLogger(TestTask.class.getSimpleName());

	public static void main(String[] args) {
		Task task = TaskPool.createTask("a");
		task.excute(new TaskRunner() {

			@Override
			public Object run(Object... objects) throws Throwable {
				log.info("main");
				return null;
			}
		});
		
		
		task.awaitTask();
		
		task.signalTask();

	}

}
