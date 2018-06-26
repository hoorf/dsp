package com.github.ruifengho.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.ruifengho.annotation.DspTxTransaction;
import com.github.ruifengho.service.Demo1Service;

@RestController
public class Demo1Controller {

	@Autowired
	private Demo1Service demo1Service;

	@RequestMapping("/demo1/client")
	@DspTxTransaction
	public void excute() {
		demo1Service.excute();
	}
}
