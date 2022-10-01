package com.register.service.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.register.service.auth.jwt.JwtUser;
import com.register.service.dto.BaseUserDTO;
import com.register.service.dto.UserDTO;
import com.register.service.entities.BaseRole;
import com.register.service.service.BaseUserService;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/v1/api/user")
public class BaseUserController {
	
	private final  BaseUserService userService;

	public BaseUserController(BaseUserService userService) {
		super();
		this.userService = userService;
	}
	
	
	/**
	 *  Devuelve todos los usuarios activos 
	 * @return
	 */
	@PreAuthorize("hasRole('"+BaseRole.ROLE_ADMIN+"')")
	@GetMapping("/getUsers")
	public ResponseEntity<List<UserDTO>> getAllUsers(@AuthenticationPrincipal JwtUser jwtUser){
		
		// esto es una prueba de push 
		return ResponseEntity.ok(userService.getAllusers(jwtUser));
	}
	
	/**
	 *  Agrega role a los usuarios
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@PostMapping("addRoles")
	@PreAuthorize("hasRole('"+BaseRole.ROLE_ADMIN+"')")
	public ResponseEntity<String> setRolesTousers(@RequestParam int userId, @RequestParam int roleId ) {

		return ResponseEntity.ok(userService.setRolesToUser(userId, roleId));
	}
	
	
	/**
	 * Borra roles de los usuarios 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@DeleteMapping("deleteRoles")
	@PreAuthorize("hasRole('"+BaseRole.ROLE_ADMIN+"')")
	public ResponseEntity<String> deleteRolesTousers(@RequestParam int userId, @RequestParam int roleId ) {

		return ResponseEntity.ok(userService.deleteRolesToUser(userId, roleId));
	}
	
	/**
	 * Borra usarios 
	 * @param userId
	 * @return
	 */
	@DeleteMapping("deleteUser")
	@PreAuthorize("hasRole('"+BaseRole.ROLE_ADMIN+"')")
	public ResponseEntity<String> deleteUser(@RequestParam int userId) {

		return ResponseEntity.ok(userService.deleteUser(userId));
	}
	
	/**
	 * Actualiza usuarios activos 
	 * @param baseUserDTO
	 * @return
	 */
	@PostMapping("updateUser")
	@PreAuthorize("hasRole('"+BaseRole.ROLE_ADMIN+"')")
	public ResponseEntity<UserDTO> updateUser(@RequestBody BaseUserDTO baseUserDTO) {

		return ResponseEntity.ok(userService.updateUser(baseUserDTO));
	}

}
