-- 유저 추가
CREATE USER 'soon90986'@'%' IDENTIFIED BY '비밀번호입력';
GRANT ALL PRIVILEGES ON soon9086.* TO 'soon9086'@'%';
FLUSH PRIVILEGES;
-- 추가 확인
SELECT User, Host FROM mysql.user;
-- 권한 확인
SHOW GRANTS FOR 'soon9086'@'%';
-- 데이터베이스 생성
CREATE DATABASE soon9086 CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
-- DB 선택 후 테이블 생성
USE soon9086;  -- 사용할 데이터베이스 선택
-- 테이블 생성
CREATE TABLE board (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    updated_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

SELECT user, host, plugin FROM mysql.user WHERE user='soon9086';

ALTER USER 'soon9086'@'%' IDENTIFIED WITH mysql_native_password BY '1234';
FLUSH PRIVILEGES;