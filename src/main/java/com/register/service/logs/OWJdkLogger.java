package com.register.service.logs;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.ConsoleHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.StreamHandler;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;


@Component
public class OWJdkLogger implements BLCLogger {
	
	private static final String DEFAULT_LOG = "logs/webapps.log";
	
	@Autowired(required = false)
	private ServletContext context;
	
	private static Logger logger = null;

	private static String federation = "NON_INITIALIZED";
	
	private static int level = BLCLogger.INFO;
	
	Properties environmentProperties, genericProperties;
	
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	/* CONSTRUCTORS */
	
	public OWJdkLogger() {
		String loggerType = System.getProperty("loggerType");
		if ((loggerType == null) || !loggerType.equals("OwJdkLogger"))
			return;

		if(OWJdkLogger.logger != null) {
			return;
		}

		// Properties
		loadProperties();
		
		OWJdkLogger.level = getOWLoggerLevelValue(getProperty("level"));
		String file = getProperty("file");
		
		if(file == null) {
			file = DEFAULT_LOG;
		}
		
		// Logger
		OWJdkFormatter formatter = new OWJdkFormatter();
		OWJdkLogger.logger = Logger.getLogger("OWLogger");
		OWJdkLogger.logger.setUseParentHandlers(false);
		OWJdkLogger.logger.setLevel(Level.INFO);
		
		// Console handler
		ConsoleHandler consoleHandler = new ConsoleHandler();
		consoleHandler.setFormatter(formatter);
		OWJdkLogger.logger.addHandler(consoleHandler);
		
		// File handler
		if(!file.isEmpty()) {
		    try {
		    	OWJdkLogger.logger.addHandler(new StreamHandler(new FileOutputStream(file, true), formatter));
			} catch(Exception e) {
				System.out.println("No s'ha trobat o no es pot crear el fitxer de log... ignorem file appender");
			}
		}
	    
	    this.info("OWJdkLogger set up (" + file + ", " + getOWLoggerLevelString(OWJdkLogger.level) + ")");
	}
	
	@PostConstruct
	public void init() {
		if(context != null) {
			if(context.getInitParameter("federacio") != null) {
				federation = context.getInitParameter("federacio").toUpperCase();
			}
		}
		else {
			federation = "TEST";
		}
	}
	
	/* PROPERTIES */
	
	private void loadProperties() {
		environmentProperties = new Properties();
		genericProperties = new Properties();
		
		try {
			environmentProperties.load(new ClassPathResource("jdklogging." + System.getProperty("spring.profiles.active") + ".properties").getInputStream());
		} catch (IOException e) {
			//
		}
		
		try {
			genericProperties.load(new ClassPathResource("jdklogging.properties").getInputStream());
		} catch (IOException e) {
			//
		}
	}
	
	private String getProperty(String property) {
		String value = environmentProperties.getProperty(property);
		
		if(value == null) {
			value = genericProperties.getProperty(property);
		}
		
		return value;
	}
	
	/* LEVEL */
	
	public void setLevel(int level) {
		OWJdkLogger.level = level;
	}
	
	private String getOWLoggerLevelString(int level) {
		switch(level) {
		case BLCLogger.DEBUG:
			return "DEBUG";
		case BLCLogger.INFO:
			return "INFO";
		case BLCLogger.WARN:
			return "WARN";
		case BLCLogger.ERROR:
			return "ERROR";
		default:
			return "DEFAULT";
		}
	}
	
	private int getOWLoggerLevelValue(String level) {
		if("DEBUG".equalsIgnoreCase(level)) {
			return BLCLogger.DEBUG;
		}

		if("INFO".equalsIgnoreCase(level)) {
			return BLCLogger.INFO;
		}

		if("WARN".equalsIgnoreCase(level)) {
			return BLCLogger.WARN;
		}

		if("ERROR".equalsIgnoreCase(level)) {
			return BLCLogger.ERROR;
		}
		
		return BLCLogger.INFO;
	}
	
	public boolean isDebugEnabled() {
		return level == BLCLogger.DEBUG;
	}
	
	/* DEBUG */
	
	public void debug(String message, Object object) {
		this.log(BLCLogger.DEBUG, message, object);
	}

	public void debug(String message) {
		this.log(BLCLogger.DEBUG, message, null);
	}
	
	/* INFO */
	
	public void info(String message, Object object) {
		this.log(BLCLogger.INFO, message, object);
	}
	
	public void info(String message) {
		this.log(BLCLogger.INFO, message, null);
	}
	
	/* WARN */
	
	public void warn(String message, Object object) {
		this.log(BLCLogger.WARN, message, object);
	}
	
	public void warn(String message) {
		this.log(BLCLogger.WARN, message, null);
	}
	
	/* ERROR */
	
	public void error(String message, Object object) {
		this.log(BLCLogger.ERROR, message, object);
	}
	
	public void error(String message) {
		this.log(BLCLogger.ERROR, message, null);
	}
	
	/* LOG */
	
	@SuppressWarnings("unchecked")
	private void log(int level, String message, Object object) {
		if(level < OWJdkLogger.level) {
			return;
		}
		
		String clazz = Thread.currentThread().getStackTrace()[3].getClassName();
		String method = Thread.currentThread().getStackTrace()[3].getMethodName();
		String time = sdf.format(new Date());
		
		OWJdkLogger.logger.info(getOWLoggerLevelString(level) + " [" + time + "]" + " :: " + federation + " :: " + clazz + "." + method + "(...) :: " + message);
		
		if(object != null) {
			if(object instanceof Throwable) {
				((Throwable) object).printStackTrace();
			}
			else {
				JSONObject json = new JSONObject();
				json.put("object", object);
				OWJdkLogger.logger.info(json.toString());
			}
		}
		
		for(Handler handler : OWJdkLogger.logger.getHandlers()) {
			handler.flush();
		}
	}
	
	/* FORMATTER */
	
	public class OWJdkFormatter extends Formatter {

		@Override
		public String format(LogRecord record) {
			return record.getMessage() + "\n";
		}
		
	}

	@Override
	public void setlevel(int level) {
		// TODO Auto-generated method stub
		
	}
	
}

