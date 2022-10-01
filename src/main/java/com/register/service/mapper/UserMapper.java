package com.register.service.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.register.service.dto.BaseUserDTO;
import com.register.service.dto.SignUpDto;
import com.register.service.dto.UserDTO;
import com.register.service.entities.BaseUser;

@Mapper(componentModel = "spring")
public interface UserMapper {


	UserDTO userDTO(BaseUser baseUser);

	BaseUser userToEntity(UserDTO userDTO);
	
	BaseUserDTO toDTO(BaseUser baseUser);

	BaseUser toEntity(BaseUserDTO baseUserDTO);






	//@Mapping(target = "password", ignore = true)
	//User signUpToUser(SignUpDto signUpDto);

	@Mapping(target = "password", ignore = true)
	BaseUser signUpToBaseUser(SignUpDto signUpDto);

}
