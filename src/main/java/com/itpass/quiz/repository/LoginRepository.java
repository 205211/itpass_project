package com.itpass.quiz.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.itpass.quiz.entity.UserAccount;

public interface LoginRepository extends CrudRepository<UserAccount, Integer> {
	@Query("SELECT username, password FROM account WHERE username = :userName")
	UserAccount findUser(String userName);

	@Query("SELECT user_id FROM account WHERE username = :userName")
	Integer findAccountId(String userName);

	@Query("SELECT tec_level, man_level, str_level FROM account WHERE username = :user_name")
	UserAccount findLevelList(String user_name);
	
	@Modifying
	@Query("UPDATE account SET tec_level = :userLevel WHERE username = :user_name")
	void changeTecLevel(Integer userLevel, String user_name);
	
	@Modifying
	@Query("UPDATE account SET str_level = :userLevel WHERE username = :user_name")
	void changeStrLevel(Integer userLevel, String user_name);
	
	@Modifying
	@Query("UPDATE account SET man_level = :userLevel WHERE username = :user_name")
	void changeManLevel(Integer userLevel, String user_name);
}