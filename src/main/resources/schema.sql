CREATE TABLE product (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price BIGINT NOT NULL,
                          description VARCHAR(255),
                          image_url VARCHAR(255)
);

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS wishes (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      product_id BIGINT NOT NULL,
                                      user_id BIGINT NOT NULL,
                                      amount INT NOT NULL,
                                      is_deleted BOOLEAN NOT NULL
);