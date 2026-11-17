package com.hexagon.hrproject.spring.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import com.hexagon.hrproject.domain.Account;

public class GoogleAccountMatcherTest {

	private static final String SUB = "google-sub-1";

	private static Account account(String googleSub, int enabled) {
		Account account = new Account();
		account.setGoogleSub(googleSub);
		account.setEnabled(enabled);
		return account;
	}

	private static final List<Account> NONE = Collections.emptyList();

	@Test
	public void sub로_찾은_계정이_있으면_이메일과_관계없이_그_계정() {
		Account bySub = account(SUB, 1);

		GoogleAccountMatcher.Result result = GoogleAccountMatcher.match(bySub, NONE, false, SUB);

		assertSame(bySub, result.getAccount());
		assertFalse(result.isSaveSub());
		assertNull(result.getErrorCode());
	}

	@Test
	public void 검증된_이메일의_계정이_하나면_그_계정이고_sub를_저장한다() {
		Account byEmail = account(null, 1);

		GoogleAccountMatcher.Result result =
				GoogleAccountMatcher.match(null, Arrays.asList(byEmail), true, SUB);

		assertSame(byEmail, result.getAccount());
		assertTrue(result.isSaveSub());
		assertNull(result.getErrorCode());
	}

	@Test
	public void 이메일의_계정이_없으면_account_not_linked() {
		GoogleAccountMatcher.Result result = GoogleAccountMatcher.match(null, NONE, true, SUB);

		assertEquals("account_not_linked", result.getErrorCode());
		assertNull(result.getAccount());
	}

	@Test
	public void 이메일의_계정이_둘_이상이면_account_ambiguous() {
		List<Account> byEmail = Arrays.asList(account(null, 1), account(null, 1));

		GoogleAccountMatcher.Result result = GoogleAccountMatcher.match(null, byEmail, true, SUB);

		assertEquals("account_ambiguous", result.getErrorCode());
		assertNull(result.getAccount());
	}

	@Test
	public void 이메일로_찾은_계정에_다른_sub가_있으면_account_not_linked() {
		List<Account> byEmail = Arrays.asList(account("other-sub", 1));

		GoogleAccountMatcher.Result result = GoogleAccountMatcher.match(null, byEmail, true, SUB);

		assertEquals("account_not_linked", result.getErrorCode());
		assertNull(result.getAccount());
	}

	@Test
	public void 이메일이_검증되지_않았으면_email_not_verified() {
		List<Account> byEmail = Arrays.asList(account(null, 1));

		GoogleAccountMatcher.Result result = GoogleAccountMatcher.match(null, byEmail, false, SUB);

		assertEquals("email_not_verified", result.getErrorCode());
		assertNull(result.getAccount());
	}

	@Test
	public void sub로_찾은_계정이_비활성이면_account_disabled() {
		GoogleAccountMatcher.Result result =
				GoogleAccountMatcher.match(account(SUB, 0), NONE, true, SUB);

		assertEquals("account_disabled", result.getErrorCode());
		assertNull(result.getAccount());
	}

	@Test
	public void 이메일로_찾은_계정이_비활성이면_sub를_저장하지_않고_account_disabled() {
		List<Account> byEmail = Arrays.asList(account(null, 0));

		GoogleAccountMatcher.Result result = GoogleAccountMatcher.match(null, byEmail, true, SUB);

		assertEquals("account_disabled", result.getErrorCode());
		assertNull(result.getAccount());
		assertFalse(result.isSaveSub());
	}
}
