<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>로그인</title>
    <style>
        * {
            box-sizing: border-box;
            font-family: "Pretendard", sans-serif;
        }

        body {
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            background: linear-gradient(135deg, #74b9ff, #a29bfe);
            margin: 0;
        }

        .login-container {
            background: white;
            width: 360px;
            padding: 40px 35px;
            border-radius: 20px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.15);
            text-align: center;
        }

        .login-container h2 {
            margin-bottom: 25px;
            color: #2d3436;
        }

        .login-container input {
            width: 100%;
            padding: 12px 15px;
            margin: 10px 0;
            border: 1px solid #dcdde1;
            border-radius: 10px;
            font-size: 15px;
            transition: border-color 0.3s;
        }

        .login-container input:focus {
            border-color: #74b9ff;
            outline: none;
        }

        .login-container button {
            width: 100%;
            padding: 12px;
            background: #0984e3;
            color: white;
            border: none;
            border-radius: 10px;
            font-size: 16px;
            cursor: pointer;
            transition: background 0.3s;
            margin-top: 10px;
        }

        .login-container button:hover {
            background: #74b9ff;
        }

        .login-container .links {
            margin-top: 15px;
            font-size: 14px;
        }

        .login-container .links a {
            color: #636e72;
            text-decoration: none;
        }

        .login-container .links a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<form class="login-container" action="${pageContext.request.contextPath}/member/login" method="post">
    <h2>로그인</h2>
    <input type="text" name="username" placeholder="아이디" required>
    <input type="password" name="password" placeholder="비밀번호" required>
    <button type="submit">로그인</button>
    <div class="links">
        <a href="#">비밀번호 찾기</a> |
        <a href="#">회원가입</a>
    </div>
</form>
</body>
</html>
