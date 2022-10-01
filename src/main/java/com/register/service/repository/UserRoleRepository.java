package com.register.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.register.service.entities.UserRoles;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoles, Integer> {
	
	Optional<UserRoles> findByUserIdAndRoleId(int userId, int roleId);
	
	List<UserRoles> findByUserId(int userId);
	
	

}
