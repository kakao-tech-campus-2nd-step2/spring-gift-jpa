CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    image_url VARCHAR(255)
);

CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(244) NOT NULL
);

CREATE TABLE wishlist (
    memberId BIGINT NOT NULL,
    productId BIGINT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY (memberId, productId),
    FOREIGN KEY (memberId) REFERENCES members(id),
    FOREIGN KEY (productId) REFERENCES product(id)
);