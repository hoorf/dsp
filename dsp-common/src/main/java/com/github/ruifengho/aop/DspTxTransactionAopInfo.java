package com.github.ruifengho.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;

import com.github.ruifengho.annotation.DspTxTransaction;

public class DspTxTransactionAopInfo {

	private Class<? extends Object> clazz;

	private DspTxTransaction annotation;

	private Method method;

	private Object[] args;

	public DspTxTransactionAopInfo(ProceedingJoinPoint point) {
		super();
		this.args = point.getArgs();
		MethodSignature signature = (MethodSignature) point.getSignature();
		this.method = signature.getMethod();
		this.clazz = point.getTarget().getClass();
		this.annotation = method.getAnnotation(DspTxTransaction.class);
	}

	public Class<? extends Object> getClazz() {
		return clazz;
	}

	public DspTxTransaction getAnnotation() {
		return annotation;
	}

	public Method getMethod() {
		return method;
	}

	public Object[] getArgs() {
		return args;
	}

}
