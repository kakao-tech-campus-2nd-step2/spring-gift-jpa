CREATE TABLE Product
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    price    INT          NOT NULL,
    imageUrl VARCHAR(255) NOT NULL
);

CREATE TABLE Users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    isDelete INT          NOT NULL
);

CREATE TABLE Wish
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    userId    BIGINT NOT NULL,
    productId BIGINT NOT NULL,
    FOREIGN KEY (userId) REFERENCES Users (id),
    FOREIGN KEY (productId) REFERENCES Product (id)
);