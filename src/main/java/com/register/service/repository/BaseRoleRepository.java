package com.register.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.register.service.entities.BaseRole;



@Repository
public interface BaseRoleRepository extends JpaRepository<BaseRole, Integer> {
	
	BaseRole findByName(String name);
	
	Optional<BaseRole>  findById(int id);

}
