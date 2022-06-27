package com.isaiah.sketchframe.repository;

import com.isaiah.sketchframe.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //	Simple method for finding user by username
    User findByUsername(String username);
}
