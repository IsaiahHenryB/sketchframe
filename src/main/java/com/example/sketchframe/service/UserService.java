package com.example.sketchframe.service;

import com.example.sketchframe.model.User;
import com.example.sketchframe.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
	User save(UserRegistrationDto registrationDto);
}
