package com.hexagon.hrproject.service;

// 구글 계정을 DB 계정에 연결하지 못했을 때 사유를 담아 던진다
public class GoogleLoginException extends RuntimeException {

	private final GoogleLoginError error;

	public GoogleLoginException(GoogleLoginError error) {
		super(error.getCode());
		this.error = error;
	}

	public GoogleLoginError getError() {
		return error;
	}
}
