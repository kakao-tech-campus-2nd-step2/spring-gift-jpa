DROP TABLE IF EXISTS wishlist;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price INT NOT NULL,
                          imageUrl VARCHAR(255) NOT NULL
);

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist (
                          userId VARCHAR(255) NOT NULL,
                          productId BIGINT NOT NULL,
                          PRIMARY KEY (userId, productId),
                          FOREIGN KEY (userId) REFERENCES users(email) ON DELETE CASCADE,
                          FOREIGN KEY (productId) REFERENCES products(id) ON DELETE CASCADE
);