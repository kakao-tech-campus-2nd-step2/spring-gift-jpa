DROP TABLE Product IF EXISTS;
DROP TABLE Member IF EXISTS;
DROP TABLE Wishlist IF EXISTS;

CREATE TABLE Product (
    id BIGINT,
    name VARCHAR(255) NOT NULL,
    price BIGINT NOT NULL,
    imageUrl VARCHAR(255),
    primary key (id)
);

CREATE TABLE Member (
    email VARCHAR(255),
    password VARCHAR(50),
    primary key (email)
);

CREATE TABLE Wishlist (
    email VARCHAR(255),
    productId BIGINT NOT NULL,
    primary key (email, productId)
);