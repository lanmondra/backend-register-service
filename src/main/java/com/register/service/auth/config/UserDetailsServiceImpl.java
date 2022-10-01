package com.register.service.auth.config;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.register.service.auth.jwt.JwtUser;
import com.register.service.entities.BaseUser;
import com.register.service.service.BaseUserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	
	private final  BaseUserService baseUserService;

	public UserDetailsServiceImpl(@Lazy BaseUserService baseUserService) {
		super();
		this.baseUserService = baseUserService;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		BaseUser baseUser = baseUserService.findUserByEmail(email);

		return JwtUser.build(baseUser);

	}

}
