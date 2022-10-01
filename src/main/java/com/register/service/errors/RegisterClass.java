package com.register.service.errors;

import java.util.Map;

import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;

import com.register.service.logs.RegisterLogClass;

@PropertySource(ignoreResourceNotFound = true, value = "classpath:application.properties")
public class RegisterClass extends RegisterLogClass {

	public void notifyError(String errorCode, HttpStatus httpStatus) throws RegisterException {

		throw new RegisterException(errorCode, httpStatus);

	}

	public void notifyError(String errorCode) throws RegisterException {
		throw new RegisterException(errorCode, HttpStatus.BAD_REQUEST);
	}

	public void notifyError(String errorCode, Object[] args) throws RegisterException {
		throw new RegisterException(errorCode, args, HttpStatus.BAD_REQUEST);
	}

	public void notifyError(String errorCode, Object[] args, HttpStatus httpStatus) throws RegisterException {
		throw new RegisterException(errorCode, args, httpStatus);
	}

	public void notifyError(String errorCode, Object[] args, HttpStatus httpStatus, Map<String, String> extraInfo)
			throws RegisterException {
		RegisterException aforityException = new RegisterException(errorCode, args, httpStatus);
		aforityException.extraInfo(extraInfo);
		throw aforityException;
	}

}
