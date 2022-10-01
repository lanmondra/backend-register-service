package com.register.service.entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
@Entity
@Table(name = "base_user")
public class BaseUser  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final int REGISTER_STATUS_CREATED = 0;
    public static final int REGISTER_STATUS_VALIDATED = 1;
    public static final int REGISTER_STATUS_COMPLETED = 2;
	
		@Id
	 	@Column(name = "id")
		@GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	 	@NotNull
	    @Column(name = "uuid", nullable = false)
	    private String uuid = UUID.randomUUID().toString();

	 	@Size(max = 45)
		@Column(name = "name", length = 45)
		private String name;
	 	
	 	@Size(max = 45)
		@Column(name = "first_last_name", length = 45)
		private String lastName;
	 	
	 	@Size(max = 45)
		@Column(name = "second_last_name", length = 45)
		private String secondLastName;
	 		 	 
	    @Size(max = 45)
	    @Column(name = "email", length = 45)
	    private String email;
	    
	    @Size(max = 100)
	    @Column(name = "password", length = 100)
	    private String password;
	    
	    @Size(max = 40)
	    @Column(name = "recover_pw", length = 40)
	    private String recoverPassword;

	    @Size(max = 15)
	    @Column(name = "phone", length = 15)
	    private String phone;

	    @NotNull
	    @Column(name = "register_status", nullable = false)
	    private Integer registerStatus;

	    @Size(max = 2)
	    @NotNull
	    @Column(name = "default_lang", nullable = false)
	    private String defaultLang;

	    
	    @ManyToMany(fetch = FetchType.LAZY)
	    @JoinTable(name = "user_roles", 
	          joinColumns = @JoinColumn(name = "user_id"), 
	          inverseJoinColumns = @JoinColumn(name = "role_id"))
	    private List<BaseRole> roles = new ArrayList<>();
	    //private Set<BaseRole> roles = new HashSet<>();
	    
	

	    
	    @Column(name = "created")
	    private LocalDateTime created;
	    
	    @Column(name = "updated")
	    private LocalDateTime updated;
	    
	    @Column(name = "deleted")
	    private LocalDateTime deleted;
	    
	    

}
