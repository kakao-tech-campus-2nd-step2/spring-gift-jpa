DROP TABLE IF EXISTS Product;

CREATE TABLE Product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    price BIGINT,
    temperatureOption VARCHAR(255),
    cupOption VARCHAR(255),
    sizeOption VARCHAR(255),
    imageurl VARCHAR(255)
);
