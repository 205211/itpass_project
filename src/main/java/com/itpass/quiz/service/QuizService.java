package com.itpass.quiz.service;

import java.util.Optional;

import com.itpass.quiz.entity.Quiz;

public interface QuizService {

	// 全件取得	
	Iterable<Quiz> selectAll();
	// idをキーに1件取得
	Optional<Quiz> selectOneById(Integer id);

	List<Quiz> getAllQuiz();

	/** 問題の取得方法を変更したため削除
	// クイズをランダムで1件取得
	Optional<Quiz> selectOneRandomQuiz();
	// 任意の問題を取得
	Optional<Quiz> chooseQuiz(Integer target, Integer divide);
	**/

	// 正解/不正解を判定
	Boolean checkQuiz(Integer id, Integer myAnswer);
	// 問題の正解の選択肢の確認
	Integer checkQuizAnswer(Integer id);
	// 登録
	void insertQuiz(Quiz quiz);
	// 更新
	void updateQuiz(Quiz quiz);
	// 削除
	void deleteQuizById(Integer id);

	// 最初のテスト用の問題を取得
	List<Integer> selectDivideOne(); //テクノロジー系列	
	List<Integer> selectDivideTwo(); //マネジメント系列
	List<Integer> selectDivideThree(); //ストラテジー系列
	
	// 各系列のランクごとの問題を取得
	// テクノロジー系列
	Optional<Quiz> selectTec1();
	Optional<Quiz> selectTec2();
	Optional<Quiz> selectTec3();
	Optional<Quiz> selectTec4();
	// マネジメント系列
	Optional<Quiz> selectMan1();
	Optional<Quiz> selectMan2();
	Optional<Quiz> selectMan3();
	Optional<Quiz> selectMan4();
	// ストラテジー系列
	Optional<Quiz> selectStr1();
	Optional<Quiz> selectStr2();
	Optional<Quiz> selectStr3();
	Optional<Quiz> selectStr4();
}
