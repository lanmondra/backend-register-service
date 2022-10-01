package com.register.service.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.register.service.entities.BaseUser;


@Repository
public interface BaseUserRepository  extends JpaRepository<BaseUser, Integer>  {
	
	
	//@Query("select msg from Message msg where msg.user.id in :ids order by msg.createdDate desc");
	
	Optional<BaseUser> findByUuid(String userUuid);

    Optional<BaseUser> findByEmailAndDeleted(String email, LocalDateTime deleted); 
    
    Optional<BaseUser> findByName(String name);
    
    Optional<BaseUser> findByNameAndEmail(String name, String email);
    
    Optional<BaseUser> findById(Long id);
    
    Optional<BaseUser> findByEmail(String email);
    
    
    

}
