-- member 테이블 생성
CREATE TABLE members (
    id UUID PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
);


-- product 테이블 생성
CREATE TABLE Product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price BIGINT,
    temperatureOption VARCHAR(255),
    cupOption VARCHAR(255),
    sizeOption VARCHAR(255),
    imageurl VARCHAR(255)
);

-- wishlist 테이블 생성
CREATE TABLE wishlist (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id BIGINT,
    member_id UUID
);
