DROP TABLE IF EXISTS product;
CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255),
                         price BIGINT,
                         imageUrl VARCHAR(255)
);