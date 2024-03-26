package com.smarts.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smarts.entities.User;

public interface UserRepository extends JpaRepository<User,Integer> {

}
