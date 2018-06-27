package com.github.ruifengho.datasource.aspect;

import java.sql.Connection;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.github.ruifengho.datasource.connection.DspConnection;
import com.github.ruifengho.modal.TxTaskLocal;

@Aspect
@Component
public class DataSourceAspect {

	private static final Logger logger = LoggerFactory.getLogger(DataSourceAspect.class);

	@Around("execution(* javax.sql.DataSource.getConnection(..))")
	public Connection around(ProceedingJoinPoint point) throws Throwable {

		logger.debug("getConnection-start---->");

		Connection connection = (Connection) point.proceed();

		if (!StringUtils.isBlank(TxTaskLocal.current())) {
			logger.debug("get dsp connection control ----->");
			connection = new DspConnection(connection);
		}

		logger.debug("connection-->" + connection);

		logger.debug("getConnection-end---->");

		return connection;
	}

}
