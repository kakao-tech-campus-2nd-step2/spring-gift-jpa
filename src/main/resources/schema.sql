CREATE TABLE products
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(255),
    price    INT,
    imageUrl VARCHAR(255)
);
CREATE TABLE users(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) UNIQUE  NOT NULL,
    password VARCHAR(255) NOT NULL
);
CREATE TABLE wishlist(
    userId BIGINT NOT NULL,
    productId BIGINT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (userId, productId),
    FOREIGN KEY (userId) REFERENCES users(id),
    FOREIGN KEY  (productId) REFERENCES  products(id)
);
