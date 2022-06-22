package com.isaiah.sketchframe.service;

import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
