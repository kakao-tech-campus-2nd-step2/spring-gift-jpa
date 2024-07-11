CREATE TABLE IF NOT EXISTS product (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL CHECK (TRIM(name) <> ''),
    price INT CHECK (price >= 0),
    imageUrl VARCHAR(255) NOT NULL CHECK (TRIM(imageUrl) <> '')
    );

CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS wish (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_email VARCHAR(255) NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product(id),
    FOREIGN KEY (member_email) REFERENCES member(email)
    UNIQUE (member_email, product_id)
);
