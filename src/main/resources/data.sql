-- 기존 데이터 삭제
DELETE FROM wishlist;
DELETE FROM products;
DELETE FROM users;

-- 새로운 데이터 삽입
INSERT INTO products (name, price, image_url) VALUES ('Sample Product', 1000, 'https://via.placeholder.com/150');
INSERT INTO users (email, password) VALUES ('test@example.com', 'password123');

-- wishlist 데이터 삽입
INSERT INTO wishlist (user_id, product_id)
SELECT u.email, p.id
FROM users u, products p
WHERE u.email = 'test@example.com' AND p.name = 'Sample Product';