<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원가입</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/member.css">
</head>
<body>
<div class="form-container">
    <h2>회원가입</h2>
    <form action="${pageContext.request.contextPath}/member/register" method="post">
        <div class="input-group">
            <label for="username">이름</label>
            <input type="text" id="username" name="username" placeholder="이름을 입력하세요" required>
        </div>

        <div class="input-group">
            <label for="email">이메일</label>
            <input type="email" id="email" name="email" placeholder="example@email.com" required>
        </div>

        <div class="input-group">
            <label for="password">비밀번호</label>
            <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
        </div>

        <div class="input-group">
            <label for="confirm">비밀번호 확인</label>
            <input type="password" id="confirm" name="confirm" placeholder="비밀번호를 다시 입력하세요" required>
        </div>

        <button type="submit">회원가입</button>
    </form>
    <p class="login-link">
        이미 계정이 있으신가요?
        <a href="${pageContext.request.contextPath}/member/login">로그인</a>
    </p>
</div>

<script src="${pageContext.request.contextPath}/js/member.js"></script>
</body>
</html>
