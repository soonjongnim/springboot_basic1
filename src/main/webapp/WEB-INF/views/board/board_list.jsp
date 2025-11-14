<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시판 목록</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <style>
        body { font-family: 'Segoe UI', sans-serif; background: #f2f4f7; margin:0; padding:0; }
        main { max-width: 900px; margin: 50px auto; padding: 20px; background: #fff; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.1); }
        h1 { text-align: center; margin-bottom: 25px; }
        .message { text-align: center; padding: 12px; margin-bottom: 20px; background: #e0ffe0; border: 1px solid #4caf50; border-radius: 8px; color: #2d7a2d; }
        .btn-write { display: inline-block; margin-bottom: 20px; padding: 10px 18px; background: #4caf50; color: #fff; border-radius: 8px; text-decoration: none; transition: 0.2s; }
        .btn-write:hover { background: #45a049; }
        table { width: 100%; border-collapse: collapse; }
        th, td { padding: 12px 15px; border-bottom: 1px solid #ddd; text-align: left; }
        th { background: #f5f5f5; }
        td.content { max-width: 500px; white-space: pre-wrap; word-wrap: break-word; }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<main>
    <h1>게시판 목록</h1>

    <c:if test="${not empty message}">
        <div class="message">${message}</div>
    </c:if>

    <a href="${pageContext.request.contextPath}/board/write" class="btn-write">글 작성</a>

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
                <td>${status.index + 1}</td>
                <td>${post.title}</td>
                <td class="content">${post.content}</td>
                <td>${post.date}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</main>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
