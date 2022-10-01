package com.register.service.logs;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;




@Component
public class RegisterLogClass {
	

	@Value("${spring.profiles.active}")
	protected String PROFILE;
	
	protected static String loggerType=null;
	
	protected static BLCLogger log;
	
	
	
	// Definicions de formats
		public static final String JSON_DATE_FORMAT = "yyyy-MM-dd";
		public static final String JSON_DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
		public static final String JSON_TIMESTAMP_FORMAT = "yyyy-MM-dd HH:mm:ss+0000";
		public static final String LONG_DATE_TIME_FORMAT = "dd/MM/yyyy HH:mm:ss";
		public static final String SHORT_DATE_FORMAT = "dd/MM/yyyy";
		public static final String HOUR_FORMAT = "HH:mm:ss";

		public static final SimpleDateFormat formatDataHora = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		public static final SimpleDateFormat formatDataDefecte = new SimpleDateFormat("dd/MM/yyyy");
		public static final SimpleDateFormat formatDataCurta = new SimpleDateFormat("dd/MM/yy"); 
		public static final SimpleDateFormat formatDataCurtaGuions = new SimpleDateFormat("dd-MM-yy"); 
		public static final SimpleDateFormat formatHora = new SimpleDateFormat("HH:mm");
		public static final SimpleDateFormat formatHoraCompleta = new SimpleDateFormat("HH:mm:ss");
		public static final SimpleDateFormat formatDateSQL = new SimpleDateFormat("yyyy-MM-dd");
		public static final SimpleDateFormat formatDatetimeSQL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		public static final SimpleDateFormat formatDatetimeMs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		public static final SimpleDateFormat formatDatetimeOnlyDigits = new SimpleDateFormat("yyyyMMddHHmmss");
		public static final SimpleDateFormat formatDay = new SimpleDateFormat("dd");
		public static final SimpleDateFormat formatMonth = new SimpleDateFormat("MM");
		public static final SimpleDateFormat formatYear = new SimpleDateFormat("yyyy");

		// Formatejadors per java.time
		protected DateTimeFormatter jsonDateFormat = DateTimeFormatter.ofPattern(JSON_DATE_FORMAT);
		protected DateTimeFormatter jsonDateTimeFormat = DateTimeFormatter.ofPattern(JSON_DATETIME_FORMAT);
		protected DateTimeFormatter sqlDateTimeFormat = DateTimeFormatter.ofPattern(JSON_DATETIME_FORMAT);
		protected DateTimeFormatter dateTimeFormatterDayMonthYear = DateTimeFormatter.ofPattern(LONG_DATE_TIME_FORMAT);
		protected DateTimeFormatter shortDateFormat = DateTimeFormatter.ofPattern(SHORT_DATE_FORMAT);
		protected DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern(HOUR_FORMAT);
	
	
	public RegisterLogClass() {
		super();
		log = getLogger();
	}


	private BLCLogger getLogger() {
		if (loggerType == null) {
			loggerType = System.getProperty("loggerType") != null ? System.getProperty("loggerType") : "Slf4jLogger";
		}


		return ((loggerType != null) && loggerType.equalsIgnoreCase("OWJdkLogger")) ? new OWJdkLogger() : new Slf4jLogger(this.getClass());
	}
	
	public void syso(String message, Boolean debug) {
		if(debug) {
			if (PROFILE.equalsIgnoreCase("prod"))
				return;
		}
		
		System.out.println("[" + formatDataHora.format(new Date()) + "] " + this.getClass().getSimpleName() + " :: " + message);
	}
	
	public void syso(String message) {
		syso(message, true);
	}
	
//	public void syso(Integer message) {
//		syso(String.valueOf(message), true);
//	}
	
	public void syso(Object message) {
		syso(String.valueOf(message), true);
	}
	

}
