<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 등록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/boardWrite.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main>
    <h1>게시글 등록</h1>
    <form action="${pageContext.request.contextPath}/board/save" method="post">
        <div class="form-group">
            <label for="title">제목</label>
            <input type="text" id="title" name="title" placeholder="제목을 입력하세요" required>
        </div>

        <div class="form-group">
            <label for="content">내용</label>
            <textarea id="content" name="content" placeholder="내용을 입력하세요" required></textarea>
        </div>

        <div class="form-footer">
            <button type="submit" class="btn btn-submit">등록하기</button>
            <a href="/board/list" class="btn btn-cancel">취소</a>
        </div>
    </form>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
