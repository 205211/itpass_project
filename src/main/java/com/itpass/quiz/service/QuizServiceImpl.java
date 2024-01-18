package com.itpass.quiz.service;

import java.util.Optional;
import java.util.List;

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
	public List<Quiz> getAllQuiz(){
		return repository.getAllQuiz();
	}

	/** 問題の取得の方法を変更したため削除
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
	**/

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
	public Integer checkQuizAnswer(Integer id) {
		Optional<Quiz> optQuiz = repository.findById(id);
		Quiz entity = optQuiz.get();
		Integer q_answer = entity.getAnswer();
		return q_answer;
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
	
	@Override
	public List<Integer> selectDivideOne() {
		return repository.getDivideOne();
	}
	
	@Override
	public List<Integer> selectDivideTwo() {
		return repository.getDivideTwo();
	}
	
	@Override
	public List<Integer> selectDivideThree() {
		return repository.getDivideThree();
	}

	@Override
	public Optional<Quiz> selectTec1() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findTec1();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectTec2() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findTec2();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectTec3() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findTec3();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectTec4() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findTec4();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectMan1() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findMan1();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectMan2() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findMan2();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectMan3() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findMan3();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectMan4() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findMan4();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectStr1() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findStr1();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectStr2() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findStr2();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectStr3() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findStr3();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}

	@Override
	public Optional<Quiz> selectStr4() {
		// TODO 自動生成されたメソッド・スタブ
		Integer num = repository.findStr4();
		if (num == null) {
			return Optional.empty();
		}
		return repository.findById(num);
	}
}
