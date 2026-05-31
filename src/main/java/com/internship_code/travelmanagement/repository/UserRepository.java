package com.internship_code.travelmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.internship_code.travelmanagement.entity.User;

public interface UserRepository
extends JpaRepository<User,Long>{

    User findByEmail(String email);

}
