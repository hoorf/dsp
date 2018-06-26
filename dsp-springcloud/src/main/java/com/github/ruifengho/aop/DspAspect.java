package com.github.ruifengho.aop;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.github.ruifengho.DspConstants;

@Aspect
@Order(Ordered.HIGHEST_PRECEDENCE)
public class DspAspect {

	private static final Logger log = LoggerFactory.getLogger(DspAspect.class);

	@Resource
	private AspectService aspectService;

	@Around("@annotation(com.github.ruifengho.annotation.DspTxTransaction)")
	public void around(ProceedingJoinPoint point) throws Throwable {
		log.debug("拦截到");
		RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();

		HttpServletRequest request = requestAttributes == null ? null
				: ((ServletRequestAttributes) requestAttributes).getRequest();
		String groupId = request.getHeader(DspConstants.DSP_TX_GROUP);
		log.debug("group-id【{}】", groupId);
		aspectService.around(groupId, point);
		log.debug("拦截结束");
	}

}
