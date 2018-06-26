package com.github.ruifengho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ruifengho.annotation.DspTxTransaction;
import com.github.ruifengho.client.Demo1Client;

@RestController
public class DemoController {

	@Autowired
	private Demo1Client demo1Client;

	@RequestMapping("/run")
	@DspTxTransaction(isStart = true)
	public void run() {
		demo1Client.excute();
		System.err.println("执行完了");
	}
}
