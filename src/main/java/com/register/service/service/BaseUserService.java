package com.register.service.service;

import java.nio.CharBuffer;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.register.service.auth.jwt.JwtUser;
import com.register.service.dto.BaseUserDTO;
import com.register.service.dto.UserDTO;
import com.register.service.entities.BaseRole;
import com.register.service.entities.BaseUser;
import com.register.service.entities.UserRoles;
import com.register.service.mapper.UserMapper;
import com.register.service.repository.BaseRoleRepository;
import com.register.service.repository.BaseUserRepository;
import com.register.service.repository.UserRoleRepository;

/**
 * 
 * @author miguelmondragon
 *
 */
@Service
@Transactional
public class BaseUserService extends AbstractService {
	

	private final PasswordEncoder passwordEncoder;

	private final UserMapper userMapper;

	private final BaseUserRepository baseUserRepository;

	private final BaseRoleRepository baseRoleRepository;

	private final ValidatorService validatorService;

	private final UserRoleRepository userRoleRepository;
	

	public BaseUserService(PasswordEncoder passwordEncoder, UserMapper userMapper, BaseUserRepository baseUserRepository,
						   BaseRoleRepository baseRoleRepository, ValidatorService validatorService,
						   UserRoleRepository userRoleRepository) {
		super();
		this.passwordEncoder = passwordEncoder;
		this.userMapper = userMapper;
		this.baseUserRepository = baseUserRepository;
		this.baseRoleRepository = baseRoleRepository;
		this.validatorService = validatorService;
		this.userRoleRepository = userRoleRepository;
	}



	/**
	 * Añade roles a los usuarios
	 * 
	 * @param idUser
	 * @param idRole
	 * @return
	 */

	public String setRolesToUser(int idUser, int idRole) {

		BaseRole baseRole =findRoleById(idRole);

		BaseUser baseUser = findUserById(idUser);

		this.existRole(baseRole, baseUser);
		// test

		UserRoles userRoles = new UserRoles();
		userRoles.setRoleId(baseRole.getId());
		userRoles.setUserId(baseUser.getId().intValue());

		userRoleRepository.save(userRoles);

		return "Añadido correcamente";
	}
	
	/**
	 * Borra un role 
	 * @param idUser
	 * @param idRole
	 * @return
	 */
	public String deleteRolesToUser(int idUser, int idRole) {
		
		BaseRole baseRole =findRoleById(idRole);

		BaseUser baseUser = findUserById(idUser);
		
		UserRoles userRoles = findByUserIdAndRoleId(baseUser.getId().intValue(), baseRole.getId());
		
		userRoleRepository.delete(userRoles);
		
		return "Role borrado correctamente";
		
	}
	
	/**
	 * Borra un usuario 
	 * @param userId
	 * @return
	 */
	public String deleteUser(int userId) {
		
		BaseUser baseUser = findUserById(userId);
		
		baseUser.setDeleted(LocalDateTime.now());
		
		saveBaseUser(baseUser);
		
		return "Usuario Borrado correctamente";
	}
	
	/**
	 * Actualiza un usuario
	 * @param baseUserDTO
	 * @return
	 */
	public UserDTO updateUser(BaseUserDTO baseUserDTO) {
		
		BaseUser baseUser = findUserByEmail(baseUserDTO.getEmail());
		baseUser.setName(baseUserDTO.getName());
		baseUser.setLastName(baseUserDTO.getLastName());
		baseUser.setSecondLastName(baseUserDTO.getSecondLastName());
		
	
		return userMapper.userDTO(baseUser);
	}

	/**
	 * Comprueba que el usuario ya tenh¡ga o no ese role
	 * 
	 * @param baseRole
	 * @param baseUser
	 */
	private void existRole(BaseRole baseRole, BaseUser baseUser) {

		Optional<UserRoles> optUserRoles = userRoleRepository.findByUserIdAndRoleId(baseUser.getId().intValue(),
				baseRole.getId());

		if (optUserRoles.isPresent()) {
			notifyError(IBaseUserServiceErrorCode.ROLE_EXIST);
		}

	}

	/**
	 * Devuelve todos los usuarios
	 * 
	 * @return
	 */
	public List<UserDTO> getAllusers(JwtUser jwtUser) {

		List<UserDTO> salida = new ArrayList<>();

		List<BaseUser> lista = baseUserRepository.findAll();
		for (BaseUser user : lista) {
			if (!user.getEmail().equalsIgnoreCase(jwtUser.getEmail())) {
				
				if(user.getDeleted() == null) {
					salida.add(userMapper.userDTO(user));
				}
				
			}

		}

		return salida;

	}
	
	/**
	 * 
	 * @param userId
	 * @param roleId
	 * @return
	 */
	private UserRoles findByUserIdAndRoleId(int userId, int roleId) {
		
		Optional<UserRoles> optUserRoles = userRoleRepository.findByUserIdAndRoleId(userId,roleId);
		if(!optUserRoles.isPresent()) {
			notifyError(IBaseUserServiceErrorCode.USER_ROLE_NO_PRESENT);
		}
		
		return optUserRoles.get();
				
	}

	/**
	 * Busaca un usuario por email
	 * 
	 * @param email
	 * @return
	 */
	public BaseUser findUserByEmail(String userEmail) {

		Optional<BaseUser> optBaseUser = baseUserRepository.findByEmailAndDeleted(userEmail, null);

		if (!optBaseUser.isPresent()) {
			notifyError(IBaseUserServiceErrorCode.EMAIL_NOT_FOUND);
		}

		return optBaseUser.get();

	}

	/**
	 * Busaca por id
	 * 
	 * @param id
	 * @return
	 */

	private BaseUser findUserById(int userId) {

		Optional<BaseUser> optBaseUser = baseUserRepository.findById(Long.valueOf(userId));
		if (!optBaseUser.isPresent()) {
			notifyError(IBaseUserServiceErrorCode.USER_NOT_FOUND);
		}
		return optBaseUser.get();

	}

	/**
	 * Busca un role por id 
	 * @param id
	 * @return
	 */
	private BaseRole findRoleById(int roleId) {
		Optional<BaseRole> optBaseRole = baseRoleRepository.findById(roleId);
		
		if(!optBaseRole.isPresent()) {
			notifyError(IBaseUserServiceErrorCode.ROLE_NOT_FOUND);
		}
		
		return optBaseRole.get();

	}

	/**
	 * Comprueba y guarda datos 
	 * @param baseUserDTO
	 * @param baseRole
	 * @return
	 */
	private BaseUser checkDataAndSave(BaseUserDTO baseUserDTO, BaseRole baseRole) {

		validatorService.validateNotNull(baseUserDTO.getName(), IValidatorServiceErrorCode.FIELD_REQUIRED);
		validatorService.validateNotNull(baseUserDTO.getLastName(), IValidatorServiceErrorCode.FIELD_REQUIRED);
		validatorService.validateEmail(baseUserDTO.getEmail(), IValidatorServiceErrorCode.BAD_EMAIL_FORMAT);

		BaseUser baseUser = userMapper.toEntity(baseUserDTO);
		// baseUser.setBaseRole(baseRole);
		baseUser.setPassword(passwordEncoder.encode(CharBuffer.wrap(baseUserDTO.getPassword())));
		baseUser.setCreated(LocalDateTime.now());
		baseUser.setDefaultLang("ES");
		baseUser.setRegisterStatus(BaseUser.REGISTER_STATUS_COMPLETED);

		saveBaseUser(baseUser);

		return baseUser;
	}

	/**
	 * Guarda los dataos
	 * 
	 * @param baseUser
	 */
	private BaseUser saveBaseUser(BaseUser baseUser) {

		return baseUserRepository.save(baseUser);
	}

}
