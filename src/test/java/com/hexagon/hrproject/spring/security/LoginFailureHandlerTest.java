package com.hexagon.hrproject.spring.security;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2Error;

import com.hexagon.hrproject.service.GoogleLoginError;

public class LoginFailureHandlerTest {

	@Test
	public void 계정_판정_사유_코드는_그대로_넘긴다() {
		for (GoogleLoginError error : GoogleLoginError.values()) {
			String code = LoginFailureHandler.errorCode(
					new OAuth2AuthenticationException(new OAuth2Error(error.getCode())));

			assertEquals(error.getCode(), code);
		}
	}

	@Test
	public void 그_밖의_OAuth2_오류는_login_failed() {
		String code = LoginFailureHandler.errorCode(
				new OAuth2AuthenticationException(new OAuth2Error("access_denied")));

		assertEquals("login_failed", code);
	}

	@Test
	public void OAuth2가_아닌_인증_예외는_login_failed() {
		String code = LoginFailureHandler.errorCode(new BadCredentialsException("bad"));

		assertEquals("login_failed", code);
	}
}
