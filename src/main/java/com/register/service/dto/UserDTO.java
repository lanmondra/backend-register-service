package com.register.service.dto;

import java.io.Serializable;
import java.util.Set;

import com.register.service.entities.BaseRole;

import lombok.Data;

@Data
public class UserDTO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int id;
	
	private String Name;
	
	private String uuid;
	  
    private String lastName;
    
    private String secondLastName;
    
    private String email;
        	    
    private Set<BaseRole> roles;
    

}
