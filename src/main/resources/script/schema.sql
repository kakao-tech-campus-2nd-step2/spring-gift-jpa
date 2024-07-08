CREATE TABLE Product (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price INT NOT NULL CHECK (price >= 0),
                          image_url TEXT,
                          is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE AppUser (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      email VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(100) NOT NULL,
                      is_active BOOLEAN NOT NULL DEFAULT TRUE,
                      role VARCHAR(50) NOT NULL DEFAULT 'USER',
                      salt VARCHAR(255)
);

CREATE TABLE Wish (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    userId BIGINT NOT NULL,
                    productId BIGINT NOT NULL,
                    quantity INT DEFAULT 1,
                    is_active BOOLEAN DEFAULT TRUE,
                    FOREIGN KEY (userId) REFERENCES AppUser(id),
                    FOREIGN KEY (productId) REFERENCES Product(id)
);
