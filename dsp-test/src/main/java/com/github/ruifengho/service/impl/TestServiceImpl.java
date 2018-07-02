package com.github.ruifengho.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.github.ruifengho.service.TestService;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private JdbcTemplate template;

	@Override
	public void run() {
		System.err.println("test run");
		template.update("insert into t_test(name) values (?)", "test");
		System.err.println("test end");
	}

}
