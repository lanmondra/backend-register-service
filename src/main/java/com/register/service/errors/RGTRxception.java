package com.register.service.errors;

import java.util.Map;

import org.springframework.http.HttpStatus;



public class RGTRxception extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private Map<String, String> extraInfo;
	private String code;
	
	private HttpStatus status;


	public RGTRxception(String message, String code, HttpStatus status) {
		super(message);

		this.code = code;
		this.status = status;
	}


	public RGTRxception(String message, HttpStatus status) {
		this(message, "", status);
	}
	
	public RGTRxception(HttpStatus status) {
		this(null, status);
	}
	
	public RGTRxception(String message) {
		this(message, HttpStatus.BAD_REQUEST);
	}
	
	public String getCode() {
		return code;
	}
	
	public HttpStatus getStatus() {
		return status;
	}

	public RGTRxception extraInfo(Map<String, String> extraInfo) {
		this.extraInfo = extraInfo;
		return this;
	}

	public Map<String, String> getExtraInfo() {
		return extraInfo;
	}



}
