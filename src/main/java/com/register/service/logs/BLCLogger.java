package com.register.service.logs;

public interface BLCLogger {
	
	public static final int DEBUG = 1;
	
	public static final int INFO =2;
	
	public static final int WARN = 3;
	
	public static final int ERROR = 4 ;
	
	public void setlevel(int level);
	
	public boolean isDebugEnabled();
	
	public void debug(String message , Object object);
	public void debug(String message);
	
	public void info(String message , Object object);
	public void info(String message);
	
	public void warn(String message, Object object);
	public void warn(String message);
	
	public void error(String mesage , Object object);
	public void error (String mesage);
	
	
	
	
	

}
