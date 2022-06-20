package com.example.sketchframe.controller;

import com.example.sketchframe.service.UserService;
import com.example.sketchframe.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.HttpMediaTypeNotSupportedException;
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
//	Allows user ot save their registration info to the database
	@PostMapping
	public String registerUserAccount(@ModelAttribute("user") UserRegistrationDto registrationDto) throws Exception{
		try {
			userService.save(registrationDto);
			return "redirect:/login?register";
		} catch (Exception ex){
			return "redirect:/register?error";
		}

	}
	
}
