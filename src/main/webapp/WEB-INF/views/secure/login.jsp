<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <title>로그인</title>

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/login.css?v=20261106-2">

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
</head>
<body>

    <div class="container login-container"
         style="max-width: 400px; margin-top: 50px;">

        <h2>로그인</h2>

        <c:if test="${param.error != null}">
            <div class="alert alert-danger login-error" role="alert">
                <c:choose>
                    <c:when test="${param.error == 'account_not_linked'}">
                        등록된 계정과 연결되지 않은 구글 계정입니다. 관리자에게 문의해 주세요.
                    </c:when>
                    <c:when test="${param.error == 'email_not_verified'}">
                        구글 계정의 이메일이 인증되지 않았습니다.
                    </c:when>
                    <c:when test="${param.error == 'account_ambiguous'}">
                        같은 이메일로 등록된 계정이 여러 개입니다. 관리자에게 문의해 주세요.
                    </c:when>
                    <c:when test="${param.error == 'account_disabled'}">
                        비활성화된 계정입니다. 관리자에게 문의해 주세요.
                    </c:when>
                    <c:otherwise>
                        로그인에 실패했습니다. 다시 시도해 주세요.
                    </c:otherwise>
                </c:choose>
            </div>
        </c:if>

        <c:if test="${param.logout != null}">
            <div class="alert alert-info" role="status">
                로그아웃되었습니다.
            </div>
        </c:if>

        <a class="btn btn-primary btn-block"
           href="${pageContext.request.contextPath}/oauth2/authorization/google">
            Google로 로그인
        </a>
    </div>

</body>
</html>
