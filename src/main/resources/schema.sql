CREATE TABLE PRODUCT (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(15) NOT NULL,
    price INT NOT NULL,
    imageUrl VARCHAR(255) NOT NULL
);

CREATE TABLE MEMBER (
    email VARCHAR(255) PRIMARY KEY,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE WISHED_PRODUCT (
    member_email VARCHAR(255),
    product_id BIGINT,
    amount INT NOT NULL,
    PRIMARY KEY(member_email, product_id),
    FOREIGN KEY(member_email) REFERENCES MEMBER(email),
    FOREIGN KEY(product_id) REFERENCES PRODUCT(id)
);