package com.isaiah.sketchframe.controller;

import com.isaiah.sketchframe.service.UserService;
import com.isaiah.sketchframe.web.dto.UserRegistrationDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
//Main Controller for user registration page
@Controller
@RequestMapping("/register")
public class UserRegistrationController {
	
	@Autowired
	private UserService userService;
//	Adding transaction logging to the userRegistrationController;
	Logger logger = LoggerFactory.getLogger(UserRegistrationController.class);
//	Gives this controller access to the userservice class
	public UserRegistrationController(UserService userService) {
		super();
		this.userService = userService;
	}
//	Adds the model attribute user to the controller register mapping
	@ModelAttribute("user")
	public UserRegistrationDto userRegistrationDto() {
		return new UserRegistrationDto();
	}
//	Returns the view of register.html
	@GetMapping
	public String showRegistrationForm() {
		return "register";
	}
//	Allows user ot save their registration info to the database (Has Exception Handling)
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) throws Exception{
//	Exception handling for failed  user registration
		try {
			userService.save(registrationDto);
			logger.trace("New user has been registered");
			return "redirect:/login?register";
		} catch (Exception ex){
			logger.error("New user has failed to register");
			return "redirect:/register?error";
		}

	}
	
}
