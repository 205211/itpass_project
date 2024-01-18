package com.itpass.quiz.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.itpass.quiz.entity.QuizRecord;

public interface RecordRepository extends CrudRepository<QuizRecord, Integer>{
	// 履歴
	@Modifying
	@Query("INSERT INTO itpass_record(user_name, q_id, quiz_answer, user_answer, start_time, fin_time, q_result, target, divide, level) VALUES (:user_name, :q_id, :quiz_answer, :user_answer, :start_time, :fin_time, :q_result, :target, :divide, :level)")
	@Transactional
	void insertQuizRecord(String user_name, Integer q_id, Integer quiz_answer, Integer user_answer, String start_time, String fin_time, String q_result, Integer target, Integer divide, Integer level);

	// 各系列のランクごとの履歴
	// テクノロジー系列
	@Query("SELECT q_result FROM itpass_record WHERE target = 1 AND level = 1 AND user_name = :user_name ORDER BY record_id DESC limit 20")
	List<String> tecResultList1(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 1 AND level = 2 AND user_name = :user_name ORDER BY record_id DESC limit 15")
	List<String> tecResultList2(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 1 AND level = 3 AND user_name = :user_name ORDER BY record_id DESC limit 10")
	List<String> tecResultList3(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 1 AND level = 4 AND user_name = :user_name")
	List<String> tecResultList4(String user_name);

	// マネジメント系列
	@Query("SELECT q_result FROM itpass_record WHERE target = 2 AND level = 1 AND user_name = :user_name ORDER BY record_id DESC limit 20")
	List<String> manResultList1(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 2 AND level = 2 AND user_name = :user_name ORDER BY record_id DESC limit 15")
	List<String> manResultList2(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 2 AND level = 3 AND user_name = :user_name ORDER BY record_id DESC limit 10")
	List<String> manResultList3(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 2 AND level = 4 AND user_name = :user_name")
	List<String> manResultList4(String user_name);

	// ストラテジー系列
	@Query("SELECT q_result FROM itpass_record WHERE target = 3 AND level = 1 AND user_name = :user_name ORDER BY record_id DESC limit 20")
	List<String> strResultList1(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 3 AND level = 2 AND user_name = :user_name ORDER BY record_id DESC limit 15")
	List<String> strResultList2(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 3 AND level = 3 AND user_name = :user_name ORDER BY record_id DESC limit 10")
	List<String> strResultList3(String user_name);
	@Query("SELECT q_result FROM itpass_record WHERE target = 3 AND level = 4 AND user_name = :user_name")
	List<String> strResultList4(String user_name);

}