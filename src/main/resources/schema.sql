
CREATE TABLE IF NOT EXISTS products (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    image_url VARCHAR(255)
    );

CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                     name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS wishlist_products (
                                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                                 product_id BIGINT NOT NULL,
                                                 user_id BIGINT NOT NULL
);