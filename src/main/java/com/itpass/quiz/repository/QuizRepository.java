package com.itpass.quiz.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;

import com.itpass.quiz.entity.Quiz;

public interface QuizRepository extends CrudRepository<Quiz, Integer>{
    @Query("SELECT id FROM itpass_quiz ORDER BY RANDOM() limit 1")
    Integer getRandomId();
}
