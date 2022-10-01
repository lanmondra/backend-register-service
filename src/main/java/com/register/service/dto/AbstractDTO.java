package com.register.service.dto;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.register.service.errors.RegisterClass;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AbstractDTO extends RegisterClass implements Serializable {



	protected LocalDate toLocalDate(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Timestamp) {
			return  ((Timestamp)value).toLocalDateTime().toLocalDate();
		}
		else if (value instanceof LocalDate){
			return (LocalDate)value;
		}
		else if (value instanceof String) {
			return LocalDate.parse((String)value, jsonDateFormat);
		}
		else if (value instanceof Date) {
			return ((Date)value).toLocalDate();
		}
		else {
			return null;
		}
	}


	protected LocalDateTime toLocalDateTime(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Timestamp) {
			return  ((Timestamp)value).toLocalDateTime();
		}
		else if (value instanceof LocalDateTime){
			return (LocalDateTime)value;
		}
		else if (value instanceof String) {
			return LocalDateTime.parse((String)value, jsonDateTimeFormat);
		}
		else {
			return null;
		}
	}


	protected Boolean toBoolean(Object value) {
		if (value == null) {
			return null;
		}

		if (value instanceof Boolean) {
			return (Boolean)value;
		}
		else if (value instanceof Byte) {
			return ((Byte)value).intValue() == 1;
		}
		else {
			return false;
		}
	}
}
