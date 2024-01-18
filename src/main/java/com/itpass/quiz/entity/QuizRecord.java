package com.itpass.quiz.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="itpass_record")
public class QuizRecord {
	@Id
	private Integer record_id;
	private String user_name;
	private Integer q_id;
	private Integer quiz_answer;
	private Integer user_answer;
	private String start_time;
	private String fin_time;
	private String q_result;
	private Integer target;
	private Integer divide;
	private Integer level;
}
