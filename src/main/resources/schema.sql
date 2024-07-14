CREATE TABLE products (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    image_url VARCHAR(255) NOT NULL
);

CREATE TABLE members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    member_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    product_number INT NOT NULL,
    CONSTRAINT fk_wish_member_id_ref_member_id FOREIGN KEY (member_id) REFERENCES members(id),
    CONSTRAINT fk_wish_product_id_ref_product_id FOREIGN KEY (product_id) REFERENCES products(id)
);
