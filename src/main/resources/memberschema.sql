CREATE TABLE members (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         email VARCHAR(255) UNIQUE NOT NULL,
                         password VARCHAR(255) NOT NULL,
                         name VARCHAR(255) NOT NULL,
                         role VARCHAR(255) NOT NULL
);
