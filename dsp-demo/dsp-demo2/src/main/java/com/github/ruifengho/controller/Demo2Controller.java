package com.github.ruifengho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ruifengho.annotation.DspTxTransaction;
import com.github.ruifengho.service.Demo2Service;

@RestController
public class Demo2Controller {

	@Autowired
	private Demo2Service demo2Service;

	@RequestMapping("/demo2/client")
	@DspTxTransaction(noRollbackFor = ArithmeticException.class)
	public void excute() {
		demo2Service.excute();
	}
}
