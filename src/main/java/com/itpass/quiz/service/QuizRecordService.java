package com.itpass.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itpass.quiz.entity.QuizRecord;
import com.itpass.quiz.repository.RecordRepository;

@Service
public class QuizRecordService {
	@Autowired
	RecordRepository repository;
	
	public void addQuizRecord(QuizRecord q_record) {
		repository.insertQuizRecord(q_record.getUser_name() , q_record.getQ_id(), q_record.getQ_result(), q_record.getRecord_time());
	}
}
