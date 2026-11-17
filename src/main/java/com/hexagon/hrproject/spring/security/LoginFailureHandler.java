package com.hexagon.hrproject.spring.security;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class LoginFailureHandler implements AuthenticationFailureHandler {

    // GoogleOidcUserService가 정한 오류 코드는 그대로, 그 밖의 실패는 login_failed로 돌려보낸다
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {

        String errorCode = "login_failed";
        if (exception instanceof OAuth2AuthenticationException) {
            String code = ((OAuth2AuthenticationException) exception).getError().getErrorCode();
            if (isAccountError(code)) {
                errorCode = code;
            }
        }

        log.debug("로그인 실패: {}", exception.getMessage());
        response.sendRedirect(request.getContextPath() + "/login?error=" + errorCode);
    }

    private static boolean isAccountError(String code) {
        return "account_not_linked".equals(code)
                || "email_not_verified".equals(code)
                || "account_ambiguous".equals(code)
                || "account_disabled".equals(code);
    }
}
