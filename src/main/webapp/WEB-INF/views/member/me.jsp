<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>회원 정보</title>

    <!-- Bootstrap CDN -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body style="background: linear-gradient(135deg, #74b9ff, #a29bfe);">

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-6">

            <div class="card shadow-lg rounded-3">
                <div class="card-header bg-primary text-white text-center">
                    <h4 class="my-2">회원 정보</h4>
                </div>

                <div class="card-body p-4">

                    <!-- member가 존재할 때 -->
                    <c:if test="${not empty member}">
                        <form action="#" method="post">

                            <div class="mb-3">
                                <label class="form-label">회원 ID</label>
                                <input type="text" class="form-control" value="${member.id}" readonly>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">이름</label>
                                <input type="text" class="form-control" name="name" value="${member.username}" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">비밀번호</label>
                                <input type="password" class="form-control" name="password" value="${member.password}" required>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 py-2 mt-3">
                                정보 수정
                            </button>
                        </form>
                    </c:if>

                    <!-- member가 null일 때 -->
                    <c:if test="${empty member}">
                        <div class="alert alert-danger text-center fw-bold mt-3">
                            로그인 정보가 없습니다.
                        </div>
                    </c:if>

                </div>
            </div>

        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/js/member.js"></script>

</body>
</html>
