package com.itpass.quiz.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import com.itpass.quiz.entity.UserAccount;

public class AccountDetails extends User {
	private final UserAccount account;
	
	public AccountDetails(UserAccount account, Collection<GrantedAuthority> authorities) {
		
		super(account.getUsername(), account.getPassword(), true, true, true, true, authorities);
		
		this.account = account;
	}
	
	public UserAccount getAccount() {
		return account;
	}

}
