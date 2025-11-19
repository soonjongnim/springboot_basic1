<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>게시글 상세</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/boardDetail.css">
</head>
<body>
<jsp:include page="/WEB-INF/views/common/header.jsp" />
<div class="container">
    <h2>게시글 상세보기</h2>
    <div class="item">
        <span>번호</span> ${post.id}
    </div>
    <div class="item">
        <span>제목</span> ${post.title}
    </div>
    <div class="item">
        <span>작성일</span> ${post.createdAt}
    </div>
    <div class="item">
        <span>내용</span>
        <div class="content-box">${post.content}</div>
    </div>
    <!-- 삭제 POST 전송용 숨겨진 폼 -->
    <form id="deleteForm" action="/board/delete" method="post" style="display: none;">
        <input type="hidden" name="id" value="${post.id}">
    </form>
    <!-- 수정페이지 POST 전송용 숨겨진 폼 -->
    <form id="editForm" action="/board/edit" method="post" style="display: none;">
        <input type="hidden" name="id" value="${post.id}">
    </form>

    <div class="btn-area">
        <a href="/board/list" class="btn btn-list">목록</a>
        <c:choose>
            <c:when test="${not empty sessionScope.loginedMemberId}">
                <!-- 수정 버튼은 a 태그 유지 + POST form submit -->
                <a href="#" class="btn btn-edit" onclick="document.getElementById('editForm').submit(); return false;">수정</a>
                <!-- 삭제 버튼은 a 태그 유지 + POST form submit -->
                <a href="#" class="btn btn-delete" onclick="if(confirm('정말 삭제하시겠습니까?')) document.getElementById('deleteForm').submit(); return false;">삭제</a>
            </c:when>
            <c:otherwise>
            </c:otherwise>
        </c:choose>

    </div>
</div>
<jsp:include page="/WEB-INF/views/common/footer.jsp" />
</body>
</html>
