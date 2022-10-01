package com.register.service.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;



@Component
public class SpringContext implements ApplicationContextAware {
	
	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		
	}
	
	public static ApplicationContext getApplicationContext() {
		return context;
		
		
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}
	
	public static String getEnvironment() {
		Environment environment = getBean(Environment.class);
		return environment.getActiveProfiles()[0];
	}

	public static boolean isProduction() {
		return getEnvironment().equals("prod");
	}

	public static boolean isPreproduction() {
		return getEnvironment().equals("prepro");
	}

	public static boolean isDevelopment() {
		return getEnvironment().equals("des");
	}

	public static boolean isTest() {
		return getEnvironment().equals("test");
	}

}


	