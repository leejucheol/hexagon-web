package com.hexagon.hrproject.spring.security;

import java.util.List;

import com.hexagon.hrproject.domain.Account;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public final class GoogleAccountMatcher {

	private GoogleAccountMatcher() {
	}

	// bySub: 구글 sub로 찾은 계정(없으면 null), byEmail: 구글 이메일과 사원 이메일이 일치하는 계정들
	public static Result match(Account bySub, List<Account> byEmail, boolean emailVerified, String sub) {
		if (bySub != null) {
			return checkEnabled(bySub, false);
		}
		if (!emailVerified) {
			return error("email_not_verified");
		}
		if (byEmail.isEmpty()) {
			return error("account_not_linked");
		}
		if (byEmail.size() > 1) {
			return error("account_ambiguous");
		}
		Account account = byEmail.get(0);
		// sub로 찾지 못했으므로 저장된 sub가 있으면 다른 구글 계정의 것이다
		if (account.getGoogleSub() != null) {
			return error("account_not_linked");
		}
		return checkEnabled(account, true);
	}

	private static Result checkEnabled(Account account, boolean saveSub) {
		if (account.getEnabled() != 1) {
			return error("account_disabled");
		}
		return new Result(account, saveSub, null);
	}

	private static Result error(String errorCode) {
		return new Result(null, false, errorCode);
	}

	@Getter
	@AllArgsConstructor(access = AccessLevel.PRIVATE)
	public static final class Result {
		private final Account account;
		private final boolean saveSub;
		private final String errorCode;
	}
}
