package com.isaiah.sketchframe.service;

import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.web.dto.UserRegistrationDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService{
//	Abstract method for saving the new users (Made so that BcryptPasswordEncoder can have access to password field)
	User save(UserRegistrationDto registrationDto);
//	Method created to check whether a user with a specific username already exists in the database or not
	boolean doesUsernameExist(String username);
}
