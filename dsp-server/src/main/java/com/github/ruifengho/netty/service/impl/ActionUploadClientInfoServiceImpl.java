package com.github.ruifengho.netty.service.impl;

import org.springframework.stereotype.Service;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.DspAction;
import com.github.ruifengho.netty.service.ActionService;

@Service(DspConstants.ACTION_UPLOAD_CLIENT_MSG)
public class ActionUploadClientInfoServiceImpl implements ActionService {

	@Override
	public String execute(String channelAddress, DspAction action) {
		return String.format("[%s] upload success", channelAddress);
	}

}
