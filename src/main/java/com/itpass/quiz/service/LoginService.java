package com.itpass.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itpass.quiz.entity.UserAccount;
import com.itpass.quiz.repository.LoginRepository;

@Service
public class LoginService {
	@Autowired
	LoginRepository repository;

    public Integer accountId(String userName) {
		return repository.findAccountId(userName);
	}

	public UserAccount findLevelList(String user_name){
		return repository.findLevelList(user_name);
	}
	
	public void updateTecLevel(Integer userLevel, String user_name) {
		repository.changeTecLevel(userLevel, user_name);
	}
	
	public void updateStrLevel(Integer userLevel, String user_name) {
		repository.changeStrLevel(userLevel, user_name);
	}
	
	public void updateManLevel(Integer userLevel, String user_name) {
		repository.changeManLevel(userLevel, user_name);
	}
}
