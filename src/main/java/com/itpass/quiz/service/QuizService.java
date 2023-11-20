package com.itpass.quiz.service;

import java.util.Optional;

import com.itpass.quiz.entity.Quiz;

public interface QuizService {

	// 全件取得	
	Iterable<Quiz> selectAll();
	// idをキーに1件取得
	Optional<Quiz> selectOneById(Integer id);
	// クイズをランダムで1件取得
	Optional<Quiz> selectOneRandomQuiz();
	// 任意の問題を取得
	Optional<Quiz> chooseQuiz(Integer target, Integer divide);
	// 正解/不正解を判定
	Boolean checkQuiz(Integer id, Integer myAnswer);
	// 登録
	void insertQuiz(Quiz quiz);
	// 更新
	void updateQuiz(Quiz quiz);
	// 削除
	void deleteQuizById(Integer id);
}