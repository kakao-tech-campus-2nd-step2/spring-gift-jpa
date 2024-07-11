CREATE TABLE products (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price DECIMAL(10, 2) NOT NULL,
                          imageUrl VARCHAR(255) NOT NULL
);

CREATE TABLE members (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(255) NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         activeToken VARCHAR(255)
);

CREATE TABLE wish_lists (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            memberId BIGINT NOT NULL,
                            productId BIGINT NOT NULL,
                            FOREIGN KEY (memberId) REFERENCES members(id),
                            FOREIGN KEY (productId) REFERENCES products(id)
);
