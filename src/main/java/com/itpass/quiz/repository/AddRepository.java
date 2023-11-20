package com.itpass.quiz.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.itpass.quiz.entity.AddUser;

public interface AddRepository extends CrudRepository<AddUser, String>{
	@Modifying
	@Query("INSERT INTO account(username, password) VALUES (:userName, :password)")
	@Transactional
	void insertUser(String userName, String password);
}
