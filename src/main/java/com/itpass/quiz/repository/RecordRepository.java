package com.itpass.quiz.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.itpass.quiz.entity.QuizRecord;

public interface RecordRepository extends CrudRepository<QuizRecord, Integer>{
	@Modifying
	@Query("INSERT INTO itpass_record(user_name, q_id, q_result, record_time) VALUES (:user_name, :q_id, :q_result, :record_time)")
	@Transactional
	void insertQuizRecord(String user_name, Integer q_id, String q_result, String record_time);
}
