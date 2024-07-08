CREATE TABLE product
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255),
    price    INT,
    imageUrl VARCHAR(255)
);

CREATE TABLE member
(
    email    VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255)
);

CREATE Table wishlist
(
    product_id   INT,
    member_email VARCHAR(255),
    PRIMARY KEY (product_id, member_email),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (member_email) REFERENCES member (email)
)