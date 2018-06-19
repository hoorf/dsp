package com.github.ruifengho.netty.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.config.ConfigReader;
import com.github.ruifengho.netty.service.ActionService;

@Service(DspConstants.ACTION_HEART)
public class ActionHeartServiceImpl implements ActionService {

	@Autowired
	private ConfigReader configReader;

	@Override
	public String execute(String channelAddress, String key, String params) {
		return String.valueOf(configReader.getTransactionNettyDelaytime());
	}

}
