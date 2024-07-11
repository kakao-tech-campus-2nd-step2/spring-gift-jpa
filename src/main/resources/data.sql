INSERT INTO products (name, price, imageUrl) VALUES ('Sample Product', 1000, 'https://via.placeholder.com/150');
INSERT INTO users (email, password) VALUES ('test@example.com', 'password123');
INSERT INTO wishlist (userId, productId)
SELECT u.email, p.id
FROM users u, products p
WHERE u.email = 'test@example.com' AND p.name = 'Sample Product';