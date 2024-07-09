DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS wishlist;
CREATE TABLE product (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price INT,
    image_Url VARCHAR(255)
);
CREATE TABLE users (
    name VARCHAR(255),
    email VARCHAR(255) PRIMARY KEY ,
    password VARCHAR(255),
    role VARCHAR(255)
);
CREATE TABLE wishlist (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    user_Id LONG NOT NULL,
    product_Id LONG NOT NULL,
    quantity INT
);