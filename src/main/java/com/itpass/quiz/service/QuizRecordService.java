package com.itpass.quiz.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itpass.quiz.entity.QuizRecord;
import com.itpass.quiz.repository.RecordRepository;

@Service
public class QuizRecordService {
	@Autowired
	RecordRepository repository;
	
	// 履歴を追加
	public void addQuizRecord(QuizRecord q_record) {
		repository.insertQuizRecord(q_record.getUser_name() , q_record.getQ_id(), q_record.getQuiz_answer(), q_record.getUser_answer(), q_record.getStart_time(), q_record.getFin_time(), q_record.getQ_result(), q_record.getTarget(), q_record.getDivide(), q_record.getLevel());
	}

	// 各系列のランクごとの履歴を取得
 	// テクノロジー系列
	public List<String> findTecList1(String userName) {
		List<String> list = repository.tecResultList1(userName);
		return list;
	}
	public List<String> findTecList2(String userName) {
		List<String> list = repository.tecResultList2(userName);
		return list;
	}
	public List<String> findTecList3(String userName) {
		List<String> list = repository.tecResultList3(userName);
		return list;
	}
	public List<String> findTecList4(String userName) {
		List<String> list = repository.tecResultList4(userName);
		return list;
	}

	// マネジメント系列
	public List<String> findManList1(String userName) {
		List<String> list = repository.manResultList1(userName);
		return list;
	}
	public List<String> findManList2(String userName) {
		List<String> list = repository.manResultList2(userName);
		return list;
	}
	public List<String> findManList3(String userName) {
		List<String> list = repository.manResultList3(userName);
		return list;
	}
	public List<String> findManList4(String userName) {
		List<String> list = repository.manResultList4(userName);
		return list;
	}

	// ストラテジー系列
	public List<String> findStrList1(String userName) {
		List<String> list = repository.strResultList1(userName);
		return list;
	}
	public List<String> findStrList2(String userName) {
		List<String> list = repository.strResultList2(userName);
		return list;
	}
	public List<String> findStrList3(String userName) {
		List<String> list = repository.strResultList3(userName);
		return list;
	}
	public List<String> findStrList4(String userName) {
		List<String> list = repository.strResultList4(userName);
		return list;
	}
}