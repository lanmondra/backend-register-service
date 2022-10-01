package com.register.service.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.register.service.ContextInfo;
import com.register.service.config.ClientContextHolder;
import com.register.service.logs.RegisterLogClass;




@Service
public class I18n extends RegisterLogClass {
	
	@Autowired
	private HttpServletRequest request;
	
	@Value("${lang.default:es}")
	private String DEFAULT_LANGUAGE;
	
	private Map<String, CustomProperties> bundles = new HashMap<>();
	
	
	/**
	 * Devuelve el idioma por defecto
	 * @return
	 */
	public String getDefaultLanguage() {
		return ContextInfo.DEFAULT_LANG;
	}
	
	
	/** Retorna el lenguage del header o el de defecto si no està definido
	 *
	 * @return
	 */
	public String getLanguage() {
		return ClientContextHolder.getLanguage() == null ? getDefaultLanguage() : ClientContextHolder.getLanguage();
	}

	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public String getMessage(String code) {
		return getMessage(code, new Object[0]);
	}
	
	/**
	 * 
	 * @param code
	 * @param args
	 * @return
	 */
	public String getMessage(String code, Object[] args) {
		String lang = request.getHeader("Accept-Language");
		
		if(lang == null || lang.length() < 2) {
			lang = DEFAULT_LANGUAGE;
		}
		
		return getMessage(code, args, lang.substring(0, 2));
	}
	
	/**
	 * 
	 * @param code
	 * @param args
	 * @param lang
	 * @return
	 */
	public String getMessage(String code, Object[] args, String lang) {
		// no language supplied, return code
		if(lang == null) {
			return code;
		}
		
		// get language file
		CustomProperties properties = getBundle(lang);
		
		// language file not found, use default language file
		if(properties == null) {
			properties = getBundle(DEFAULT_LANGUAGE);
			
			// default language file not found, return code
			if(properties == null) {
				return code;
			}
		}

		// code found, return formatted message
		if(properties.containsKey(code)) {
			return new MessageFormat(properties.getProperty((String) code)).format(args);
		}
		
		// return code
		return code;
	}
	
	/**
	 * Returns the according property file for the given language
	 * @param lang
	 * @return
	 */
	private CustomProperties getBundle(String lang) {
		if(bundles.containsKey(lang)) {
			return bundles.get(lang);
		}
		
		try {
			CustomProperties properties = new CustomProperties();
			properties.load(new ClassPathResource("messages_" + lang + ".properties").getInputStream());
			bundles.put(lang, properties);
			
			return properties;
		} catch(MissingResourceException | IOException e) {
//			e.printStackTrace();
			log.error("Property file 'messages_" + lang + ".properties' not found");
		}
		
		return null;
	}
	
	/**
	 * Custom class to read the property files
	 * @author equilibrium
	 *
	 */
	private class CustomProperties extends Properties {
		
		private static final long serialVersionUID = -4761679905620487734L;

		public void load(InputStream stream) throws IOException {
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
			String line;
			
			while((line = reader.readLine()) != null) {
				String[] split = line.split("=");
				
				if(split.length >= 2) {
					this.put(split[0], split[1]);
				}
				else if(split.length == 1) {
					this.put(split[0], split[0]);
				}
			}
		}
		
	}
	
	
	

}
