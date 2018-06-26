package com.github.ruifengho.service.impl;

import org.springframework.stereotype.Service;

import com.github.ruifengho.service.Demo2Service;

@Service
public class Demo2ServiceImpl implements Demo2Service {

	@Override
	public void excute() {
		System.err.println("demo2 run");

	}

}
