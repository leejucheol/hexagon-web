package com.hexagon.hrproject.spring.security;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Component;

import com.hexagon.hrproject.domain.Account;
import com.hexagon.hrproject.mapper.AccountMapper;

import lombok.RequiredArgsConstructor;

// 구글 로그인 사용자를 DB 계정에 연결해 AccountDetails로 돌려준다
@Component
@RequiredArgsConstructor
public class GoogleOidcUserService implements OAuth2UserService<OidcUserRequest, OidcUser> {

	private final AccountMapper accountMapper;

	private final OidcUserService delegate = new OidcUserService();

	@Override
	public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
		OidcUser oidcUser = delegate.loadUser(userRequest);
		String sub = oidcUser.getSubject();

		GoogleAccountMatcher.Result result = GoogleAccountMatcher.match(
				accountMapper.selectByGoogleSub(sub),
				accountMapper.selectByEmpEmail(oidcUser.getEmail()),
				Boolean.TRUE.equals(oidcUser.getEmailVerified()),
				sub);

		if (result.getErrorCode() != null) {
			throw new OAuth2AuthenticationException(new OAuth2Error(result.getErrorCode()));
		}

		Account account = result.getAccount();
		if (result.isSaveSub()) {
			accountMapper.updateGoogleSub(account.getAccountId(), sub);
			account = accountMapper.selectByGoogleSub(sub);
		}

		return new AccountDetails(account, oidcUser);
	}
}
