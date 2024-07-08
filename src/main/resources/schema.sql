CREATE TABLE product (
       id BIGINT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(255) NOT NULL,
       price INTEGER NOT NULL,
       image_url VARCHAR(255)
);

CREATE TABLE "user" (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        email VARCHAR(255) NOT NULL UNIQUE,
        password VARCHAR(255) NOT NULL
);

CREATE TABLE wish (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        user_id BIGINT,
        product_id BIGINT,
        FOREIGN KEY (user_id) REFERENCES "user"(id),
        FOREIGN KEY (product_id) REFERENCES product(id)
);
