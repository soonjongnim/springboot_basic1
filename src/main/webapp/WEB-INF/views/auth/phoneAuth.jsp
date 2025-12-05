<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>휴대폰 본인인증</title>

    <!-- Bootstrap (디자인 편하게 적용) -->
    <link rel="stylesheet"
          href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"/>

    <style>
        body {
            background: #f5f7fa;
        }
        .auth-container {
            max-width: 420px;
            margin: 60px auto;
            padding: 30px;
            background: white;
            border-radius: 12px;
            box-shadow: 0 6px 20px rgba(0,0,0,0.08);
        }
        .title {
            font-size: 1.4rem;
            font-weight: 600;
            margin-bottom: 25px;
        }
    </style>
</head>

<body>

<div class="auth-container">
    <div class="title">휴대폰 본인인증</div>

    <form action="/auth/phone/send" method="post">

        <!-- 이름 -->
        <div class="mb-3">
            <label class="form-label">이름</label>
            <input type="text" name="name" class="form-control" placeholder="홍길동" required>
        </div>

        <!-- 생년월일 -->
        <div class="mb-3">
            <label class="form-label">생년월일</label>
            <input type="text" name="birth" class="form-control" placeholder="YYYYMMDD" maxlength="8" required>
        </div>

        <!-- 성별 -->
        <div class="mb-3">
            <label class="form-label">성별</label>
            <select name="gender" class="form-select" required>
                <option value="">선택</option>
                <option value="M">남</option>
                <option value="F">여</option>
            </select>
        </div>

        <!-- 통신사 -->
        <div class="mb-3">
            <label class="form-label">통신사</label>
            <select name="carrier" class="form-select" required>
                <option value="">선택</option>
                <option value="SKT">SKT</option>
                <option value="KT">KT</option>
                <option value="LGU">LGU+</option>
                <option value="MVNO">알뜰폰(MVNO)</option>
            </select>
        </div>

        <!-- 휴대전화 번호 -->
        <div class="mb-3">
            <label class="form-label">휴대전화 번호</label>
            <input type="text" name="phone" class="form-control" placeholder="01012345678" maxlength="11" required>
        </div>

        <!-- 버튼 -->
        <div class="d-grid mt-4">
            <button type="submit" class="btn btn-primary btn-lg">인증번호 받기</button>
        </div>

    </form>

</div>

</body>
</html>
