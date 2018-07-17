package com.github.ruifengho.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.ruifengho.client.Demo2Client;

@Service
public class Demo1ServiceImpl implements Demo1Service {

	@Autowired
	private Demo2Client demo2Client;

	@Autowired
	private JdbcTemplate template;

	@Override
	public void excute() {
		System.err.println("demo1 run");
		demo2Client.excute();

		System.err.println("demo1 sql begin ");
		template.update("insert into t_test(name) values (?)", "demo1");
		System.err.println("demo1 sql end ");
		System.err.println("demo1 执行完了");

	}

}
