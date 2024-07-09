-- Drop the table if it exists
DROP TABLE IF EXISTS PRODUCT;
DROP TABLE IF EXISTS Member;

-- Create the PRODUCT table
CREATE TABLE PRODUCT (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255) NOT NULL,
        price INT NOT NULL,
        image_url VARCHAR(255)
);

-- -- Create the USER table
CREATE TABLE MEMBER (
        email VARCHAR(100) PRIMARY KEY,
        password VARCHAR(100) NOT NULL,
        role VARCHAR(20) NOT NULL
);

-- Create the WishProduct
CREATE TABLE WISH_PRODUCT (
        wish_product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
        member_email VARCHAR(100) NOT NULL,
        product_id BIGINT NOT NULL,
        CONSTRAINT fk_member FOREIGN KEY (member_email) REFERENCES MEMBER(email),
        CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES PRODUCT(id)
);