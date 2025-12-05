<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>메인 페이지</title>
    <!-- 공통 CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/header.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/footer.css">
    <!-- 페이지 전용 CSS -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
</head>
<body>

<jsp:include page="/WEB-INF/views/common/header.jsp" />

<main class="card" role="main" aria-labelledby="main-title">
    <h1 id="main-title" class="title">YouTube에서 오디오 추출(MP3) 및 요약하기</h1>
    <p class="subtitle">영상 URL을 붙여넣고 버튼을 누르면 오디오 추출(MP3) 및 요약합니다.</p>

    <div class="form-row" aria-hidden="false">
        <input id="videoUrl" class="input" type="url" placeholder="https://www.youtube.com/watch?v=..." aria-label="유튜브 영상 URL" />
        <button id="convertBtn" class="btn" type="button">
            <svg id="iconPlay" width="18" height="18" viewBox="0 0 24 24" fill="none" aria-hidden="true">
                <path d="M5 3v18l15-9L5 3z" fill="currentColor"/>
            </svg>
            오디오 추출(MP3) 및 요약하기
        </button>
    </div>

    <div class="status" id="statusArea" aria-live="polite" style="display:none">
        <div class="message" id="statusText">준비됨</div>
        <div class="progress" id="progressWrap" aria-hidden="true">
            <div class="bar" id="progressBar" style="width:0%"></div>
        </div>
        <div id="resultArea" style="display:none"></div>
    </div>
</main>

<jsp:include page="/WEB-INF/views/common/footer.jsp" />

<script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>
