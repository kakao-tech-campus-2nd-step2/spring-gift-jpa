CREATE TABLE IF NOT EXISTS member (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS product (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       price INT NOT NULL,
                                       image_url VARCHAR(255) NOT NULL
);
CREATE TABLE IF NOT EXISTS wishlist (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        member_id BIGINT NOT NULL,
                                        product_name VARCHAR(255) NOT NULL,
                                        product_url VARCHAR(255) NOT NULL,
                                        FOREIGN KEY (member_id) REFERENCES member(id)
);