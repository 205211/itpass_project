package com.itpass.quiz.entity;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Quiz {
	// 問題ID 
	@Id
	private Integer id;
	// 資格試験コード
	private Integer target;
	// 分類コード
	private Integer divide;
	// 時間的難易度
	private Integer level1;
	// 内容の難易度
	private Integer level2;
	// 問題文
	private String statement;
	// 選択肢１
	private String order1;
	// 選択肢２
	private String order2;
	// 選択肢３
	private String order3;
	// 選択肢４
	private String order4;
	// 正解の選択肢の番号
	private Integer answer;
	// ヒント
	private String hint;
}