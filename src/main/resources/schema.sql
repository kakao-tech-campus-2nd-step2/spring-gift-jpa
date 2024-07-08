CREATE TABLE product (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    imageUrl VARCHAR(1000)
);

CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(1000),
    email VARCHAR(1000),
    role VARCHAR(255)
);

CREATE TABLE wishlist(
    product_id BIGINT,
    user_id BIGINT,
)