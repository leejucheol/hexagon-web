<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="javax.servlet.jsp.PageContext" %>
<%@ page import="javax.servlet.jsp.JspFactory" %>

<%!
    private String mypageText(PageContext context, String expression) {
        Object value = JspFactory.getDefaultFactory()
            .getJspApplicationContext(context.getServletContext())
            .getExpressionFactory()
            .createValueExpression(
                context.getELContext(),
                expression,
                Object.class
            )
            .getValue(context.getELContext());

        if (value == null) {
            return "";
        }

        return value.toString()
            .replace("&", "&amp;")
            .replace("<", "&lt;")
            .replace(">", "&gt;")
            .replace("\"", "&quot;")
            .replace("'", "&#39;");
    }
%>

<!DOCTYPE html>
<html lang="ko">
<head>
    <title>HRMS - 마이페이지</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="_csrf" content="${_csrf.token}">
    <meta name="_csrf_header" content="${_csrf.headerName}">

    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/hrms-mypage.css?v=password-1">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

    <%@ include file="/WEB-INF/views/common/sidebar.jspf" %>

    <div class="main-content main-area hrms-page">

        <div class="top-nav-bar">
            <div class="page-title-area">
                <h2>마이페이지</h2>
                <p class="mypage-location">홈 &gt; 마이페이지</p>
            </div>

            <%@ include file="/WEB-INF/views/common/header.jspf" %>
        </div>

        <div class="profile-card">
            <div class="profile-header">
                <div class="profile-avatar">👤</div>

                <div>
                    <h3 class="mypage-name">
                        <%= mypageText(
                            pageContext,
                            "${sessionScope.loginUser.emp.ename != null ? sessionScope.loginUser.emp.ename : '사용자'}"
                        ) %>님
                    </h3>

                    <span class="label label-primary">
                        <%= request.isUserInRole("HR_ADMIN")
                            ? "인사 관리자"
                            : "일반 사원" %>
                    </span>
                </div>
            </div>

            <h4 class="mypage-info-title">내 정보</h4>

            <div class="row">
                <div class="col-md-6 info-group">
                    <div class="info-label">아이디</div>
                    <div class="info-value">
                        <%= mypageText(
                            pageContext,
                            "${sessionScope.loginUser.loginId}"
                        ) %>
                    </div>
                </div>

                <div class="col-md-6 info-group">
                    <div class="info-label">이름</div>
                    <div class="info-value">
                        <%= mypageText(
                            pageContext,
                            "${sessionScope.loginUser.emp.ename}"
                        ) %>
                    </div>
                </div>

                <div class="col-md-6 info-group">
                    <div class="info-label">이메일</div>
                    <div class="info-value">
                        <%= mypageText(
                            pageContext,
                            "${sessionScope.loginUser.emp.email != null ? sessionScope.loginUser.emp.email : 'admin@hrms.com'}"
                        ) %>
                    </div>
                </div>

                <div class="col-md-6 info-group">
                    <div class="info-label">연락처</div>
                    <div class="info-value">
                        <%= mypageText(
                            pageContext,
                            "${sessionScope.loginUser.emp.phone != null ? sessionScope.loginUser.emp.phone : '010-1234-5678'}"
                        ) %>
                    </div>
                </div>

                <div class="col-md-6 info-group">
                    <div class="info-label">입사일</div>
                    <div class="info-value">
                        <%= mypageText(
                            pageContext,
                            "${sessionScope.loginUser.emp.hiredate != null ? sessionScope.loginUser.emp.hiredate : '2024-01-01'}"
                        ) %>
                    </div>
                </div>

                <div class="col-md-6 info-group">
                    <div class="info-label">최종 로그인</div>
                    <div class="info-value">
                        <%= mypageText(
                            pageContext,
                            "${sessionScope.loginUser.lastLoginAt != null ? sessionScope.loginUser.lastLoginAt : '방금 전'}"
                        ) %>
                    </div>
                </div>
            </div>
        </div>
    </div>

</body>
</html>