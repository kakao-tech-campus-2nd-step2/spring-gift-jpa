CREATE TABLE userInfo (
    email VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255),
    role VARCHAR(64)
);

CREATE TABLE wishlist (
    email VARCHAR(255),
    name VARCHAR(255),
    price INT,
    quantity INT
);

CREATE TABLE product (
    id bigint PRIMARY KEY,
    name VARCHAR(255),
    price INT,
    imageUrl VARCHAR(255)
);