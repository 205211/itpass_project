package com.itpass.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.QuizRecord;
import com.example.demo.repository.RecordRepository;

@Service
public class QuizRecordService {
	@Autowired
	RecordRepository repository;
	
	public void addQuizRecord(QuizRecord q_record) {
		repository.insertQuizRecord(q_record.getUser_name() , q_record.getQ_id(), q_record.getQ_result(), q_record.getRecord_time());
	}
}
