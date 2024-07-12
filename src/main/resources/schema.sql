CREATE TABLE IF NOT EXISTS products (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    image_url VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS members (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS wishes (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      member_id BIGINT NOT NULL,
                                      product_id BIGINT NOT NULL,
                                      FOREIGN KEY (member_id) REFERENCES members(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
    );