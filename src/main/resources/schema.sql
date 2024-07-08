CREATE TABLE product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    img VARCHAR(255)
);

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    UNIQUE (email)
);

CREATE TABLE wishes
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id    BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    number     BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (product_id) REFERENCES product (id)
);
