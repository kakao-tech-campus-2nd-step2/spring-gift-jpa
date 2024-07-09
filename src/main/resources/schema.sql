DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS member;

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(15) NOT NULL,
                         price INT NOT NULL,
                         imageurl VARCHAR(1000)
);

CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL
);

CREATE TABLE wish (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      member_id BIGINT NOT NULL,
                      product_id BIGINT NOT NULL,
                      FOREIGN KEY (member_id) REFERENCES member(id),
                      FOREIGN KEY (product_id) REFERENCES product(id)
);