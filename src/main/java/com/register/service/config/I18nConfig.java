package com.register.service.config;

import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class I18nConfig {

	@Bean
	@Description("Spring Message Resolver")
	public ResourceBundleMessageSource messageSource() {
		
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasename("messages");
		messageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

}
