-- Drop tables if they exist
DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

-- Create USERS table
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
);

-- Create PRODUCTS table
CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price BIGINT NOT NULL,
    image_url VARCHAR(255) NOT NULL
);

-- Create WISH table
CREATE TABLE wish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

ALTER TABLE wish ADD CONSTRAINT fk_wish_user_id FOREIGN KEY (user_id) REFERENCES users(id);
ALTER TABLE wish ADD CONSTRAINT fk_wish_product_id FOREIGN KEY (product_id) REFERENCES products(id);