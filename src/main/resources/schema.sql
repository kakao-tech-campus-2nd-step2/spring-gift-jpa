DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS wishlists;

CREATE TABLE products
(
    id       BIGINT PRIMARY KEY,
    name     VARCHAR(255)  NOT NULL,
    price    BIGINT        NOT NULL,
    imageurl VARCHAR(2083) NOT NULL
);

CREATE TABLE users
(
    email    VARCHAR(255) PRIMARY KEY,
    password VARCHAR(72) NOT NULL
);

CREATE TABLE wishlists
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_email VARCHAR(255),
    product_id BIGINT,
    count      INT NOT NULL DEFAULT 1,
    FOREIGN KEY (user_email) REFERENCES users (email),
    FOREIGN KEY (product_id) REFERENCES products (id),
    CONSTRAINT unique_user_product UNIQUE (user_email, product_id)
);
