package com.github.ruifengho.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ruifengho.client.Demo2Client;

@Service
public class Demo1ServiceImpl implements Demo1Service {

	@Autowired
	private Demo2Client demo2Client;

	@Override
	public void excute() {
		System.err.println("demo1 run");
		demo2Client.excute();
		System.err.println("demo1 执行完了");

	}

}
