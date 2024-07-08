DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS member;
DROP TABLE IF EXISTS wishlist;

CREATE TABLE member
(
    email VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price INT NOT NULL,
                         imageUrl VARCHAR(255)
);

CREATE TABLE wishlist (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          member_email VARCHAR(255) NOT NULL,
                          product_id BIGINT NOT NULL,
                          FOREIGN KEY (member_email) REFERENCES member(email),
                          FOREIGN KEY (product_id) REFERENCES product(id)
);
