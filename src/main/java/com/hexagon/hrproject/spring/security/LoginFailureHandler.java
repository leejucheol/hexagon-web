package com.hexagon.hrproject.spring.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.hexagon.hrproject.service.GoogleLoginError;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        log.debug("로그인 실패: {}", exception.getMessage());
        response.sendRedirect(request.getContextPath() + "/login?error=" + errorCode(exception));
    }

    // 계정 판정 사유는 그대로, 그 밖의 실패(구글 동의 취소 등)는 login_failed로 돌려보낸다
    static String errorCode(AuthenticationException exception) {
        if (exception instanceof OAuth2AuthenticationException) {
            String code = ((OAuth2AuthenticationException) exception).getError().getErrorCode();
            if (GoogleLoginError.fromCode(code) != null) {
                return code;
            }
        }
        return "login_failed";
    }
}
