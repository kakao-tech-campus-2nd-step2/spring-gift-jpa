DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         price BIGINT,
                         imageUrl VARCHAR(255)
);

CREATE TABLE users (
                      email VARCHAR(255) PRIMARY KEY,
                      password VARCHAR(255)
);
