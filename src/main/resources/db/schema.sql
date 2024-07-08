-- products
CREATE TABLE products (
    id LONG PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    price INT NOT NULL,
    imageUrl VARCHAR(255)
);

-- members
CREATE TABLE members (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

-- wishlist
CREATE TABLE wishlist (
    id LONG AUTO_INCREMENT PRIMARY KEY,
    memberId LONG NOT NULL,
    productId LONG NOT NULL,
    FOREIGN KEY (memberId) REFERENCES members(id),
    FOREIGN KEY (productId) REFERENCES products(id)
)