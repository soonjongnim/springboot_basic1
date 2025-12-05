<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>로그인</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>

<body class="bg-light">
<div class="container mt-5" style="max-width: 400px;">
    <h3 class="text-center mb-4">로그인</h3>

    <!-- 기본 로그인 -->
    <form action="/member/loginProcess" method="post" class="card p-4 shadow-sm">
        <div class="mb-3">
            <label class="form-label">아이디</label>
            <input type="text" class="form-control" name="username" placeholder="아이디 입력">
        </div>

        <div class="mb-3">
            <label class="form-label">비밀번호</label>
            <input type="password" class="form-control" name="password" placeholder="비밀번호 입력">
        </div>

        <button type="submit" class="btn btn-primary w-100 mt-2">로그인</button>
    </form>

    <!-- ★ 간편 로그인 (카카오) 추가 ★ -->
    <div class="text-center mt-4">
        <p class="text-muted">간편 로그인</p>

        <!-- 카카오 로그인 버튼 -->
        <a href="https://kauth.kakao.com/oauth/authorize?client_id=${kakaoClientId}&redirect_uri=${kakaoRedirectUri}&response_type=code" class="btn w-100 mb-2"
           style="background-color: #FEE500; border: 1px solid #FEE500;">
            <img src="/images/kakao_login_medium_narrow.png"
                 style="height: 40px;">
        </a>
    </div>

    <!-- 모달 -->
    <div class="modal fade" id="alertModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header bg-primary text-white">
                    <h5 class="modal-title">알림</h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body" id="modalMessage"></div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<script>
    const urlParams = new URLSearchParams(window.location.search);

    const signup = urlParams.get("signup");
    const error = urlParams.get("error");

    let msg = "";

    if (signup === "success") msg = "회원가입이 완료되었습니다!";

    if (error) {
        switch(error) {
            case "username": msg = "아이디를 입력해주세요."; break;
            case "password": msg = "비밀번호를 입력해주세요."; break;
            case "fail": msg = "아이디 또는 비밀번호가 올바르지 않습니다."; break;
        }
    }

    if (msg) {
        document.getElementById("modalMessage").innerText = msg;

        const modal = new bootstrap.Modal(document.getElementById("alertModal"));
        modal.show();

        setTimeout(() => modal.hide(), 3000);
    }
</script>

<script src="${pageContext.request.contextPath}/js/script.js"></script>
</body>
</html>
