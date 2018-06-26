package com.github.ruifengho.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.TxGroup;
import com.github.ruifengho.modal.TxGroupManager;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class DspRestTemplateInterceptor implements RequestInterceptor {

	private static final Logger log = LoggerFactory.getLogger(DspRestTemplateInterceptor.class);

	@Override
	public void apply(RequestTemplate template) {
		log.debug("添加 groupId");
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
