package com.itpass.quiz.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.itpass.quiz.entity.Quiz;

public interface QuizRepository extends CrudRepository<Quiz, Integer>{
    // すべての問題を取得
    @Query("SELECT * FROM itpass_quiz ORDER BY target ASC, divide ASC, level1 ASC, level2 ASC")
	List<Quiz> getAllQuiz();
	// 最初のテストの問題を選出
	@Query("SELECT id FROM itpass_quiz WHERE target = 1 AND (level1 = 1 OR level1 = 2) ORDER BY RANDOM() limit 5")
	List<Integer> getDivideOne();
	
	@Query("SELECT id FROM itpass_quiz WHERE target = 2 AND (level1 = 1 OR level1 = 2) ORDER BY RANDOM() limit 5")
	List<Integer> getDivideTwo();
	
	@Query("SELECT id FROM itpass_quiz WHERE target = 3 AND (level1 = 1 OR level1 = 2) ORDER BY RANDOM() limit 5")
	List<Integer> getDivideThree();
	
	// ユーザの各系列のランクによって選出する問題を調整
	// テクノロジー系列
	@Query("SELECT id FROM itpass_quiz WHERE target = 1 AND level1 = 1 ORDER BY RANDOM() limit 1")
	Integer findTec1();
	@Query("SELECT id FROM itpass_quiz WHERE target = 1 AND level1 = 2 ORDER BY RANDOM() limit 1")
	Integer findTec2();	
	@Query("SELECT id FROM itpass_quiz WHERE target = 1 AND level1 = 3 ORDER BY RANDOM() limit 1")
	Integer findTec3();	
	@Query("SELECT id FROM itpass_quiz WHERE target = 1 ORDER BY RANDOM() limit 1")
	Integer findTec4();
	
	// マネジメント系列
	@Query("SELECT id FROM itpass_quiz WHERE target = 2 AND level1 = 1 ORDER BY RANDOM() limit 1")
	Integer findMan1();
	@Query("SELECT id FROM itpass_quiz WHERE target = 2 AND level1 = 2 ORDER BY RANDOM() limit 1")
	Integer findMan2();
	@Query("SELECT id FROM itpass_quiz WHERE target = 2 AND level1 = 3 ORDER BY RANDOM() limit 1")
	Integer findMan3();
	@Query("SELECT id FROM itpass_quiz WHERE target = 2 ORDER BY RANDOM() limit 1")
	Integer findMan4();
	
	// ストラテジー系列
	@Query("SELECT id FROM itpass_quiz WHERE target = 3 AND level1 = 1 ORDER BY RANDOM() limit 1")
	Integer findStr1();	
	@Query("SELECT id FROM itpass_quiz WHERE target = 3 AND level1 = 2 ORDER BY RANDOM() limit 1")
	Integer findStr2();
	@Query("SELECT id FROM itpass_quiz WHERE target = 3 AND level1 = 3 ORDER BY RANDOM() limit 1")
	Integer findStr3();
	@Query("SELECT id FROM itpass_quiz WHERE target = 3 ORDER BY RANDOM() limit 1")
	Integer findStr4();
}