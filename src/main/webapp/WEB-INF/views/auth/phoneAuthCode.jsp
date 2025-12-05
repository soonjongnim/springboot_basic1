<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>인증번호 입력</title>

    <!-- Bootstrap CSS -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>

    <style>
        body { background: #f5f7fa; }
        .auth-container {
            max-width: 420px;
            margin: 60px auto;
            padding: 30px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
        }
        .title { font-size: 1.4rem; font-weight: 600; margin-bottom: 25px; }
        .timer { font-size: 0.9rem; color: #ff4d4f; margin-top: 5px; }
    </style>
</head>
<body>
<div class="auth-container">

    <div class="title">인증번호 입력</div>

    <!-- 안내문 -->
    <p class="text-muted" style="font-size: 0.9rem;">
        <c:choose>
            <c:when test="${not empty resendMsg}">
                ${resendMsg}
            </c:when>
            <c:otherwise>
                입력하신 휴대폰 번호로 인증번호가 발송되었습니다.<br>
                3분 안에 인증번호를 입력해주세요.
            </c:otherwise>
        </c:choose>
    </p>

    <!-- 인증번호 입력 폼 -->
    <form id="verifyForm" action="/auth/phone/verify" method="post">
        <input type="hidden" name="requestId" value="${sessionScope.PHONE_AUTH_REQUEST_ID}">
        <div class="mb-3">
            <label class="form-label">인증번호</label>
            <input type="text" name="code" class="form-control" maxlength="6"
                   placeholder="6자리 숫자 입력" required>
        </div>

        <!-- 타이머 -->
        <div id="timer" class="timer">03:00</div>

        <div class="d-grid mt-4">
            <button type="submit" class="btn btn-primary btn-lg">인증하기</button>
        </div>
    </form>

    <!-- 재전송 버튼 -->
    <div class="text-end mt-3">
        <button type="button" id="resendBtn" class="btn btn-link p-0">인증번호 재전송</button>
    </div>

    <!-- 처음 화면 버튼 -->
    <div class="text-start mt-3">
        <a href="/auth/phone" class="btn btn-secondary btn-sm">처음 화면으로</a>
    </div>

    <!-- 오류 메시지 -->
    <c:if test="${param.error == 'fail'}">
        <div class="alert alert-danger mt-3">
            인증번호가 올바르지 않습니다. 다시 시도해주세요.
        </div>
    </c:if>

</div>

<!-- 세션 체크 -->
<c:if test="${empty sessionScope.PHONE_AUTH_REQUEST_ID}">
    <script>
        alert("휴대폰 정보를 다시 입력해주세요.");
        window.location.href = "/auth/phone";
    </script>
</c:if>

<!-- 타이머 & 재전송 JS -->
<script>
    document.addEventListener("DOMContentLoaded", function() {
        let timerSec = 180; // 3분
        const timerTag = document.getElementById("timer");

        // 타이머 실행
        const interval = setInterval(() => {
            let min = String(Math.floor(timerSec / 60)).padStart(2,'0');
            let sec = String(timerSec % 60).padStart(2,'0');
            //console.log(min);
            //console.log(sec);
            timerTag.textContent = min + ":" + sec; // ✅ ES5 호환

            if(timerSec <= 0){
                clearInterval(interval);
                timerTag.textContent = "시간 초과되었습니다. 재전송 해주세요.";
            }
            timerSec--;
        }, 1000);

        // 재전송 버튼 클릭 이벤트
        document.getElementById("resendBtn").addEventListener("click", function(e){
            e.preventDefault();

            fetch('/auth/phone/resend', { method: 'POST' })
                .then(res => res.json())
                .then(data => {
                    if(data.resultCode === "0000"){
                        timerSec = 180; // 타이머 초기화
                        alert("인증번호가 재전송되었습니다.");
                    } else {
                        alert("재전송 실패: " + data.message);
                    }
                })
                .catch(err => alert("네트워크 오류"));
        });
    });
</script>
</body>
</html>
