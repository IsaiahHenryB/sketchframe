package com.isaiah.sketchframe.service;

import com.isaiah.sketchframe.model.Role;
import com.isaiah.sketchframe.model.User;
import com.isaiah.sketchframe.repository.UserRepository;
import com.isaiah.sketchframe.web.dto.UserRegistrationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    //	Implementing functionality for UserService interface
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;
    }

    //This overwrites the save method allowing Bcrypt to handle the submitted password data as an encrypted string value
    @Override
    public User save(UserRegistrationDto registrationDto) {
        User user = new User(registrationDto.getFirstname(),
                registrationDto.getLastname(),
                registrationDto.getEmail(),
                passwordEncoder.encode(registrationDto.getPassword()),
                registrationDto.getUsername(),
                Arrays.asList(new Role("ROLE_USER")));
        return userRepository.save(user);
    }

    //	Sends true or false depending on whether user with username is found in the database or not
    @Override
    public boolean doesUsernameExist(String username) {
        boolean userExists = false;
        User user = userRepository.findByUsername(username);
        if (user != null) {
            userExists = true;
        } else {
            userExists = false;
        }
        return userExists;
    }

    //This queries the database for users with corresponding username field(not case-sensitive)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid Username or Password");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    ;

}
