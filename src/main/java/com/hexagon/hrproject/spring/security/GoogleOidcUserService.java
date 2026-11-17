package com.hexagon.hrproject.spring.security;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import com.hexagon.hrproject.domain.Account;
import com.hexagon.hrproject.service.AccountService;
import com.hexagon.hrproject.service.GoogleLoginException;

import lombok.RequiredArgsConstructor;

// 구글 로그인 사용자를 DB 계정에 연결해 AccountDetails로 돌려준다
@Component
@RequiredArgsConstructor
public class GoogleOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

	private final AccountService accountService;

	private final OidcUserService delegate = new OidcUserService();

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = delegate.loadUser(userRequest);

		try {
			Account account = accountService.linkGoogleAccount(
					oidcUser.getSubject(),
					oidcUser.getEmail(),
					Boolean.TRUE.equals(oidcUser.getEmailVerified()));

			return new AccountDetails(account, oidcUser);

		} catch (GoogleLoginException e) {
			throw new OAuth2AuthenticationException(new OAuth2Error(e.getError().getCode()));
		}
	}
}
