CREATE TABLE product
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255),
    price     INT,
    image_url VARCHAR(255)
);

CREATE TABLE member
(
    email    VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255)
);

CREATE Table wishlist
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id   BIGINT,
    member_email VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    FOREIGN KEY (member_email) REFERENCES member (email) ON DELETE CASCADE
)