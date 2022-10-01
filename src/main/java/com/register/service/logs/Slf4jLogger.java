package com.register.service.logs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slf4jLogger implements BLCLogger {
	
	
private Logger logger;
	
	public Slf4jLogger(Class<?> clazz) {
		logger = LoggerFactory.getLogger(clazz);
	}


	@Override
	public boolean isDebugEnabled() {
		return logger.isDebugEnabled();
	}

	@Override
	public void debug(String message, Object object) {
		logger.debug(message, object);
	}

	@Override
	public void debug(String message) {
		logger.debug(message);
	}

	@Override
	public void info(String message, Object object) {
		logger.info(message, object);
	}

	@Override
	public void info(String message) {
		logger.info(message);
	}

	@Override
	public void warn(String message, Object object) {
		logger.warn(message, object);
	}

	@Override
	public void warn(String message) {
		logger.warn(message);
	}

	@Override
	public void error(String message, Object object) {
		logger.error(message, object);
	}

	@Override
	public void error(String message) {
		logger.error(message);
	}
	@Override
	public void setlevel(int level) {
		// TODO Auto-generated method stub
		
	}
	
}


