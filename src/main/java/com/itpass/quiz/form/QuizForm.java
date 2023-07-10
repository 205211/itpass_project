package com.itpass.quiz.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizForm {
	/** 識別ID */
	private Integer id;
	// 資格試験コード
	@NotNull
	private Integer target;
	// 分類コード
	@NotNull
	private Integer divide;
	// 時間的難易度
	@NotNull
	private Integer level1;
	// 内容の難易度
	@NotNull
	private Integer level2;
	// 問題文
	@NotBlank
	private String statement;
	// 選択肢１
	@NotBlank
	private String order1;
	// 選択肢２
	@NotBlank
	private String order2;
	// 選択肢３
	@NotBlank
	private String order3;
	// 選択肢４
	@NotBlank
	private String order4;
	// 正解の選択肢の番号
	@NotNull
	private Integer answer;
	// ヒント
	private String hint;
	// 「登録」「変更」の判定
	private Boolean newQuiz;
}