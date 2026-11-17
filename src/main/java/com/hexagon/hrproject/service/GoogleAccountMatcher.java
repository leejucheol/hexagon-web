package com.hexagon.hrproject.service;

import java.util.List;

import com.hexagon.hrproject.domain.Account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public final class GoogleAccountMatcher {

	private GoogleAccountMatcher() {
	}

	// bySub: 구글 sub로 찾은 계정(없으면 null), byEmail: 구글 이메일과 사원 이메일이 일치하는 계정들
	public static Result match(Account bySub, List<Account> byEmail, boolean emailVerified) {
		if (bySub != null) {
			return checkEnabled(bySub, false);
		}
		if (!emailVerified) {
			return error(GoogleLoginError.EMAIL_NOT_VERIFIED);
		}
		if (byEmail.isEmpty()) {
			return error(GoogleLoginError.ACCOUNT_NOT_LINKED);
		}
		if (byEmail.size() > 1) {
			return error(GoogleLoginError.ACCOUNT_AMBIGUOUS);
		}
		Account account = byEmail.get(0);
		// sub로 찾지 못했으므로 저장된 sub가 있으면 다른 구글 계정의 것이다
		if (account.getGoogleSub() != null) {
			return error(GoogleLoginError.ACCOUNT_NOT_LINKED);
		}
		return checkEnabled(account, true);
	}

	private static Result checkEnabled(Account account, boolean saveSub) {
		if (account.getEnabled() != 1) {
			return error(GoogleLoginError.ACCOUNT_DISABLED);
		}
		return new Result(account, saveSub, null);
	}

	private static Result error(GoogleLoginError error) {
		return new Result(null, false, error);
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Result {
		private final Account account;
		private final boolean saveSub;
		private final GoogleLoginError error;
	}
}
