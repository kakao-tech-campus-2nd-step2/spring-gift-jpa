DROP TABLE IF EXISTS products CASCADE;
CREATE TABLE products (
    id LONG PRIMARY KEY ,
    name VARCHAR(255),
    price INT,
    imageUrl VARCHAR(255)
);

DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255)
);

DROP TABLE IF EXISTS wishes CASCADE;
CREATE TABLE wishes (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    user_id LONG,
    product_id LONG,
    quantity INT,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);
