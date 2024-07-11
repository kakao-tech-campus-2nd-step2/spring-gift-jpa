
CREATE TABLE IF NOT EXISTS products (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    image_url VARCHAR(255)
    );


CREATE TABLE IF NOT EXISTS members (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wishes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    Member_id BIGINT,
    product_id BIGINT,
    quantity INT,
    FOREIGN KEY (Member_id) REFERENCES Members(id),
    FOREIGN KEY (product_id) REFERENCES products(id)
);

