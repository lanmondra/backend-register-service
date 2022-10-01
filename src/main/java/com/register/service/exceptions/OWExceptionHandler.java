
package com.register.service.exceptions;



import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.register.service.errors.RGTRxception;
import com.register.service.logs.RegisterLogClass;



@Component
public class OWExceptionHandler extends RegisterLogClass {

	public ResponseEntity<Object> handleException(Exception exception, WebRequest request) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

		Map<String, Object> response = new HashMap<>();
		response.put("message", exception.getMessage());
		response.put("timestamp", new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));

		if (exception instanceof RGTRxception) {
			response.put("error", ((RGTRxception) exception).getCode());
			response.put("issuer", "Register-service");

			if (((RGTRxception) exception).getExtraInfo() != null) {
				response.put("extraInfo", ((RGTRxception) exception).getExtraInfo());
			}

			log.error(response.toString(), exception);
			return new ResponseEntity<>(response, headers, ((RGTRxception) exception).getStatus());
		} else {
			log.error(exception.getMessage(), exception);
		}

		// exception.printStackTrace();

		return new ResponseEntity<>(response, headers, HttpStatus.INTERNAL_SERVER_ERROR);

	}
}
