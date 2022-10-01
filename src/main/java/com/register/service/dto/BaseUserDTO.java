package com.register.service.dto;

import lombok.Data;

@Data
public class BaseUserDTO {
	
		private int id;
		
	    private String Name;
	    
	    private String lastName;
	    
	    private String secondLastName;
	    
	    private String email;
	        	    
	    private String role; 
	    
	    private String password;

}
