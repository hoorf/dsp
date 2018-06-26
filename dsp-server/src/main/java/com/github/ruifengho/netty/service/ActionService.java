package com.github.ruifengho.netty.service;

import com.github.ruifengho.modal.DspAction;

public interface ActionService {

	String execute(String channelAddress, DspAction action);

}
