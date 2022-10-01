package com.register.service.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
@Entity
@Table(name = "base_role")
public class BaseRole implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	 	public static final String ROLE_ADMIN = "ROLE_ADMIN";
	    public static final String ROLE_USER = "ROLE_USER";
	    public static final String ROLE_CLIENT = "ROLE_CLIENT";
	  

	    @Id
	    @Column(name = "id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;

	    @NotNull
	    @Column(name = "name", nullable = false)
	    private String name;

}
