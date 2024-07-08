DROP TABLE IF EXISTS products;
CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255),
                          price INT,
                          imageUrl VARCHAR(255)
);

DROP TABLE IF EXISTS users;
CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255),
                       password VARCHAR(255)
);

DROP TABLE IF EXISTS wishes;
CREATE TABLE wishes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        product_id BIGINT NOT NULL,
                        quantity INT NOT NULL,
                        FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (product_id) REFERENCES products(id)
);