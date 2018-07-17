package com.github.ruifengho.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.ruifengho.service.Demo2Service;

@Service
public class Demo2ServiceImpl implements Demo2Service {

	@Autowired
	private JdbcTemplate template;

	@Override
	public void excute() {
		System.err.println("demo2 run");
		System.err.println("demo2 save");
		template.update("insert into t_test(name) values (?)", "demo2");
		System.out.println(1/0);
		System.err.println("demo2 run end");
	}

}
