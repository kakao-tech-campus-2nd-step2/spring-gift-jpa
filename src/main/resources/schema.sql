CREATE TABLE product
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    price     integer      NOT NULL,
    image_url VARCHAR(255)
);

CREATE TABLE member
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(20)                NOT NULL,
    email    VARCHAR(255)               NOT NULL UNIQUE,
    password VARCHAR(255)               NOT NULL,
    role     ENUM ('MEMBER', 'MANAGER') NOT NULL DEFAULT 'MEMBER'
);

CREATE TABLE wishlist
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_member
        FOREIGN KEY (member_id)
            REFERENCES member (id)
            ON DELETE CASCADE,
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES product (id)
            ON DELETE CASCADE,
    UNIQUE (member_id, product_id)
);
