<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/boardList.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<main>
    <h1>게시판 목록</h1>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>
    <c:choose>
        <c:when test="${not empty sessionScope.loginedMemberId}">
            <a href="${pageContext.request.contextPath}/board/write" class="btn-write">글 작성</a>
        </c:when>
        <c:otherwise>
            <p style="color: #ff0000; font-weight: bold; text-align: center;">로그인 정보가 없습니다.</p>
        </c:otherwise>
    </c:choose>
    <table>
        <thead>
        <tr>
            <th>번호</th>
            <th>제목</th>
            <th>내용</th>
            <th>작성일</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="post" items="${boardList}" varStatus="status">
            <tr>
                <td>${post.id}</td>
                <td>
                    <form id="detailForm-${post.id}" action="/board/detail" method="post" style="display: none;">
                        <input type="hidden" name="id" value="${post.id}">
                    </form>
                    <!-- 상세 버튼은 a 태그 유지 + POST form submit -->
                    <a href="#" onclick="document.getElementById('detailForm-${post.id}').submit(); return false;">${post.title}</a>
                </td>
                <td class="content">${post.content}</td>
                <td>${post.createdAt}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
