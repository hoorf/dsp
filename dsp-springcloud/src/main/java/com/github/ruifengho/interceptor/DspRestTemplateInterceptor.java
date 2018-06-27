package com.github.ruifengho.interceptor;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.ruifengho.DspConstants;
import com.github.ruifengho.modal.TxTaskLocal;

import feign.RequestInterceptor;
import feign.RequestTemplate;

public class DspRestTemplateInterceptor implements RequestInterceptor {

	private static final Logger log = LoggerFactory.getLogger(DspRestTemplateInterceptor.class);

	@Override
	public void apply(RequestTemplate template) {
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();

		HttpServletRequest request = requestAttributes == null ? null
				: ((ServletRequestAttributes) requestAttributes).getRequest();
		String groupId = request.getHeader(DspConstants.DSP_TX_GROUP);

		if (StringUtils.isBlank("groupId")) {
			groupId = TxTaskLocal.current();
		}

		if (groupId != null) {
			template.header(DspConstants.DSP_TX_GROUP, groupId);
		}

	}

}
