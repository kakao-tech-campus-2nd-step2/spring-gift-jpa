DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         price BIGINT,
                         imageUrl VARCHAR(255)
);

CREATE TABLE users (
                      username VARCHAR(255) PRIMARY KEY,
                      pw VARCHAR(255),
                      accessToken VARCHAR(255)
);
