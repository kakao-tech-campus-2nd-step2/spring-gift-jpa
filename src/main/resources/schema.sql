CREATE TABLE users (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    password VARCHAR(1000),
    email VARCHAR(1000),
    role VARCHAR(255)
);

CREATE TABLE wishlist(
    product_id BIGINT,
    user_id BIGINT
);