<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<header class="navbar">
    <div class="logo">🌿 MySpringSite</div>
    <nav class="menu">
        <a href="${pageContext.request.contextPath}/">홈</a>
        <a href="${pageContext.request.contextPath}/board/list">게시판</a>
        <c:choose>
            <c:when test="${not empty sessionScope.loginedMemberId}">
                <span>안녕하세요, ${member.username}님</span>
                <form action="${pageContext.request.contextPath}/member/logout" method="post" style="display:inline;">
                    <button type="submit" class="btn-logout">로그아웃</button>
                </form>
                <a class="outline" href="${pageContext.request.contextPath}/member/me">회원정보</a>
            </c:when>
            <c:otherwise>
                <a href="${pageContext.request.contextPath}/member/login">로그인</a>
                <a class="outline" href="${pageContext.request.contextPath}/member/register">회원가입</a>
            </c:otherwise>
        </c:choose>

    </nav>
</header>
