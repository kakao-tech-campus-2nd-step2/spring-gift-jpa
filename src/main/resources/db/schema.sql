CREATE TABLE products
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255),
    price    BIGINT,
    imageUrl VARCHAR(255)
);

CREATE TABLE users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL
);

CREATE TABLE wishlists
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255) NOT NULL,
    product_id BIGINT       NOT NULL,
    FOREIGN KEY (user_email) REFERENCES users (email),
    FOREIGN KEY (product_id) REFERENCES products (id)
);