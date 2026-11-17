package com.hexagon.hrproject.spring.security;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class LoginSuccessHandlerTest {

	@Test
	public void HR_ADMIN이면_대시보드로_이동한다() {
		String path = LoginSuccessHandler.targetPath(Arrays.asList(
				new SimpleGrantedAuthority("ROLE_EMPLOYEE"),
				new SimpleGrantedAuthority("ROLE_HR_ADMIN")));

		assertEquals("/dashboard", path);
	}

	@Test
	public void HR_ADMIN이_아니면_사원_홈으로_이동한다() {
		String path = LoginSuccessHandler.targetPath(Collections.singletonList(
				new SimpleGrantedAuthority("ROLE_EMPLOYEE")));

		assertEquals("/employee/home", path);
	}
}
