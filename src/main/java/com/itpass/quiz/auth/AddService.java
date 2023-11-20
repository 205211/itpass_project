package com.itpass.quiz.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.itpass.quiz.entity.AddUser;
import com.itpass.quiz.repository.AddRepository;

@Service
public class AddService {
	@Autowired
	AddRepository repository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void addUser(AddUser adduser) {
		adduser.setPassword(passwordEncoder.encode(adduser.getPassword()));
		repository.insertUser(adduser.getUserName(), adduser.getPassword());
	}
	
}
