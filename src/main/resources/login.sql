-- 사용자 테이블 생성
CREATE TABLE IF NOT EXISTS site_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);


-- 기본 사용자 추가 (예시)
INSERT INTO site_user (username, password, email)
VALUES ('user1', '1234', 'user1@example.com');
