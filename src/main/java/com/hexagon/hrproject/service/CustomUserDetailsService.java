package com.hexagon.hrproject.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.hexagon.hrproject.domain.Account;
import com.hexagon.hrproject.mapper.AccountMapper;
import com.hexagon.hrproject.spring.security.AccountDetails;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	private final AccountMapper accountMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Account account = accountMapper.selectByLoginId(username);

		if (account == null) {
			throw new UsernameNotFoundException("존재하지 않는 계정입니다: " + username);
		}

		return new AccountDetails(account);
	}

	
}
