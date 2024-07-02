DROP TABLE IF EXISTS products;
CREATE TABLE products (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price INT,
    imageUrl TEXT(65535)
);