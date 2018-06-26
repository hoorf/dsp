package com.github.ruifengho.interceptor;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.TxGroup;
import com.github.ruifengho.modal.TxGroupManager;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class DspRestTemplateInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate template) {
		TxGroup current = TxGroupManager.getInstance().getCurrent();
		String groupId = null;
		if (current != null && current.getGroupId() != null) {
			groupId = current.getGroupId();
		}

		if (groupId != null) {
			template.header(DspConstants.DSP_TX_GROUP, groupId);
		}

	}

}
