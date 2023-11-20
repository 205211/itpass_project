package com.itpass.quiz.auth;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.itpass.quiz.entity.UserAccount;
import com.itpass.quiz.repository.LoginRepository;

@Service
public class AccountDetailsService implements UserDetailsService {
	@Autowired
	LoginRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserAccount account = repository.findUser(username);
		if (account == null) {
			throw new UsernameNotFoundException("not found : " + username);
		}
		return new AccountDetails(account, getAuthorities(account));
	}
	
	private Collection<GrantedAuthority> getAuthorities(UserAccount account) {
        //認証が通った時にユーザに与える権限の範囲を設定する。
        return AuthorityUtils.createAuthorityList("ROLE_USER");
    }

}
