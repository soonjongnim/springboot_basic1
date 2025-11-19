<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>로그인</title>

    <!-- CSS 불러오기 -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<form class="login-container" action="${pageContext.request.contextPath}/member/login" method="post">
    <h2>로그인</h2>
    <input type="text" name="username" placeholder="아이디" required>
    <input type="password" name="password" placeholder="비밀번호" required>
    <button type="submit">로그인</button>
    <div class="links">
        <a href="#">비밀번호 찾기</a> |
        <a href="#">회원가입</a>
    </div>
</form>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
