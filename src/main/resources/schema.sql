CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY CHECK (id >= 1),
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    image_url VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users (
    email VARCHAR(255) NOT NULL PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS wishlist (
    email VARCHAR(255) NOT NULL,
    product_id BIGINT NOT NULL,
    PRIMARY KEY (email, product_id),
    FOREIGN KEY (email) REFERENCES users(email),
    FOREIGN KEY (product_id) REFERENCES product(id)
);