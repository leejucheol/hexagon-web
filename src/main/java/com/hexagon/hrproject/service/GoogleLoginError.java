package com.hexagon.hrproject.service;

// 구글 로그인을 거부한 사유. code는 /login?error= 값과 login.jsp의 문구 분기에 그대로 쓰인다
public enum GoogleLoginError {

	ACCOUNT_NOT_LINKED("account_not_linked"),
	EMAIL_NOT_VERIFIED("email_not_verified"),
	ACCOUNT_AMBIGUOUS("account_ambiguous"),
	ACCOUNT_DISABLED("account_disabled");

	private final String code;

	GoogleLoginError(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	// 사유 코드가 아니면 null
	public static GoogleLoginError fromCode(String code) {
		for (GoogleLoginError error : values()) {
			if (error.code.equals(code)) {
				return error;
			}
		}
		return null;
	}
}
