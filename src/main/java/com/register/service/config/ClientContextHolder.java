package com.register.service.config;

import com.register.service.ContextInfo;

public class ClientContextHolder {
	
	private static ThreadLocal<Object> context = new ThreadLocal<>();

	/**
	 * Setea el contexto para la petición actual
	 * @param tenant
	 */
    public static void setCurrentContext(Object tenant) {
        context.set(tenant);
    }

    /**
     * Recupera el contexto para la peticion actual
     */
    public static Object getCurrentContext() {
        return context.get();
    }

    /**
     * Resetea el contexto
     */
    public static void clear() { 
    	context.remove(); 
	}



    /**
     * Retorna el idioma indicado en el header de la petición
     */
    public static String getLanguage() {
        ContextInfo contextInfo = (ContextInfo)context.get();

        return contextInfo == null ? null : contextInfo.getLanguage();
    }

}
