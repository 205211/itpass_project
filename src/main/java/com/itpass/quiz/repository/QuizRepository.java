package com.itpass.quiz.repository;

import org.springframework.data.repository.CrudRepository;

import com.itpass.quiz.entity.Quiz;

public interface QuizRepository extends CrudRepository<Quiz, Integer>{

}
