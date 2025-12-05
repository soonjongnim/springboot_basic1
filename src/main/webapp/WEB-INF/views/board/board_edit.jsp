<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 수정</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/boardEdit.css">
</head>
<body style="background: linear-gradient(135deg, #74b9ff, #a29bfe);">
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<div class="container">
    <h2>게시글 수정</h2>

    <form action="/board/update" method="post">
        <input type="hidden" name="id" value="${post.id}">
        <label>제목</label>
        <input type="text" name="title" value="${post.title}" required>
        <label>내용</label>
        <textarea name="content" required>${post.content}</textarea>
        <div class="btn-area">
            <button type="submit" class="btn btn-save">수정완료</button>
            <a href="/board/list" class="btn btn-cancel">취소</a>
        </div>
    </form>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
