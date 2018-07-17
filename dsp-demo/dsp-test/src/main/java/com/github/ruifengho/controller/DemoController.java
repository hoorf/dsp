package com.github.ruifengho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ruifengho.annotation.DspTxTransaction;
import com.github.ruifengho.client.Demo1Client;
import com.github.ruifengho.service.TestService;

@RestController
public class DemoController {

	@Autowired
	private Demo1Client demo1Client;

	@Autowired
	private TestService testService;

	@RequestMapping("/run")
	@DspTxTransaction(isStart = true)
	public void run() {
		demo1Client.excute();
		System.err.println("test sql begin");
		testService.run();
		System.err.println("test sql end");
//		System.out.println(1/0);
		System.err.println("执行完了");
	}
}
