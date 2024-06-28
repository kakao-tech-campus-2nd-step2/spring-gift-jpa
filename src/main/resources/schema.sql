CREATE TABLE IF NOT EXISTS product (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description VARCHAR(500),
    price INTEGER NOT NULL,
    image_url VARCHAR(255)
);
