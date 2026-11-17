package com.hexagon.hrproject.spring.security;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class LoginSuccessHandler
        implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication)
            throws IOException, ServletException {

        // 마이페이지에서 사용하는 로그인 계정 정보 저장
        if (authentication.getPrincipal() instanceof AccountDetails) {
            AccountDetails details =
                (AccountDetails) authentication.getPrincipal();

            request.getSession().setAttribute(
                "loginUser",
                details.getAccount()
            );
        }

        response.sendRedirect(
            request.getContextPath()
                + targetPath(authentication.getAuthorities())
        );
    }

    // 역할별 이동 경로
    static String targetPath(Collection<? extends GrantedAuthority> authorities) {
        // 인사 관리자 역할 보유 여부 확인
        boolean isHrAdmin = authorities.stream()
            .anyMatch(authority ->
                "ROLE_HR_ADMIN".equals(authority.getAuthority())
            );

        return isHrAdmin
            ? "/dashboard"
            : "/employee/home";
    }
}
