<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 정보</title>
    <!-- 공통 CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <!-- 페이지 전용 CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/memberInfo.css">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<div class="info-container">
    <h2>회원 정보</h2>

    <!-- member가 존재할 때 -->
    <c:if test="${not empty member}">
        <form action="#" method="post">
            <div class="input-group">
                <label>회원 ID</label>
                <c:if test="${not empty member.id}">
                    <input type="text" name="id" value="${member.id}" readonly>
                </c:if>
            </div>
            <div class="input-group">
                <label>이름</label>
                <input type="text" name="name" value="${member.username}" required>
            </div>
            <div class="input-group">
                <label>비밀번호</label>
                <input type="password" name="password" value="${member.password}" required>
            </div>
            <button type="submit">정보 수정</button>
        </form>
    </c:if>

    <!-- member가 null일 때 -->
    <c:if test="${empty member}">
        <p style="color: red; font-weight: bold; text-align: center;">로그인 정보가 없습니다.</p>
    </c:if>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/member.js"></script>
</body>
</html>
