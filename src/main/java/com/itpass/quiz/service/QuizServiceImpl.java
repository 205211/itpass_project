package com.itpass.quiz.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itpass.quiz.entity.Quiz;
import com.itpass.quiz.repository.QuizRepository;

@Service
@Transactional
public class QuizServiceImpl implements QuizService {

	@Autowired
	QuizRepository repository;

	@Override
	public Iterable<Quiz> selectAll() {
		return repository.findAll();
	}

	@Override
	public Optional<Quiz> selectOneById(Integer id) {
		return repository.findById(id);
	}

	@Override
	public Optional<Quiz> selectOneRandomQuiz() {
		// ランダムでidの値を取得する
		Integer randId = repository.getRandomId();
		// 問題がない場合
		if (randId == null) {
			// 空のOptionalインスタンスを返します。
			return Optional.empty();
		}
		return repository.findById(randId);
	}

	@Override
	public Optional<Quiz> chooseQuiz(Integer target, Integer divide) {
		// TODO 自動生成されたメソッド・スタブ
		Integer chooseId = repository.findSelectQuiz(target, divide);
		if (chooseId == null) {
			return Optional.empty();
		}
		return repository.findById(chooseId);
	}

	@Override
	public Boolean checkQuiz(Integer id, Integer myAnswer) {
		// 正解/不正解を判定用変数
		Boolean check = false;
		// 対象の問題を取得
		Optional<Quiz> optQuiz = repository.findById(id);
		// 値存在チェック
		if (optQuiz.isPresent()) {
			Quiz quiz = optQuiz.get();
			// 解答チェック
			if (quiz.getAnswer().equals(myAnswer)) {
				check = true;
			}
		}
		return check;
	}

	@Override
	public void insertQuiz(Quiz quiz) {
		repository.save(quiz);
	}

	@Override
	public void updateQuiz(Quiz quiz) {
		repository.save(quiz);
	}

	@Override
	public void deleteQuizById(Integer id) {
		repository.deleteById(id);
	}

}
