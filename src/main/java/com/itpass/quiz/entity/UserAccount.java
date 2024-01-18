package com.itpass.quiz.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="account")
public class UserAccount {
	@Id
	private Integer user_id;
	private String username;
	private String password;
	private Integer tec_level;
	private Integer man_level;
	private Integer str_level;
}
