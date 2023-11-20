package com.itpass.quiz.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.itpass.quiz.entity.UserAccount;

public interface LoginRepository extends CrudRepository<UserAccount, Integer> {
	@Query("SELECT username, password FROM account WHERE username = :userName")
	UserAccount findUser(String userName);
}
