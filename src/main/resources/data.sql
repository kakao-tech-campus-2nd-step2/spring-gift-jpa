---- products 테이블 초기화
--INSERT INTO product (id, name, price, image_url) VALUES (1, 'Product 1', 1000, 'http://example.com/image1.jpg');
--INSERT INTO product (id, name, price, image_url) VALUES (2, 'Product 2', 2000, 'http://example.com/image2.jpg');
--INSERT INTO product (id, name, price, image_url) VALUES (3, 'Product 3', 3000, 'http://example.com/image3.jpg');
--
---- users 테이블 초기화
--INSERT INTO users (email, password) VALUES ('user1@example.com', 'password1');
--INSERT INTO users (email, password) VALUES ('user2@example.com', 'password2');
--INSERT INTO users (email, password) VALUES ('user3@example.com', 'password3');
--
---- wishlist 테이블 초기화
--INSERT INTO wishlist (email, product_id) VALUES ('user1@example.com', 1);
--INSERT INTO wishlist (email, product_id) VALUES ('user1@example.com', 2);
--INSERT INTO wishlist (email, product_id) VALUES ('user2@example.com', 2);
--INSERT INTO wishlist (email, product_id) VALUES ('user2@example.com', 3);
--INSERT INTO wishlist (email, product_id) VALUES ('user3@example.com', 1);
--INSERT INTO wishlist (email, product_id) VALUES ('user3@example.com', 3);
-- Insert users
INSERT INTO member (email, password) VALUES ('user1@example.com', '111');
INSERT INTO member (email, password) VALUES ('user3@example.com', '333');
INSERT INTO member (email, password) VALUES ('user2@example.com', '222');

-- Insert products
INSERT INTO product (name, price, image_url) VALUES ('Product 1', 1000, 'http://example.com/product1.jpg');
INSERT INTO product (name, price, image_url) VALUES ('Product 2', 2000, 'http://example.com/product2.jpg');
INSERT INTO product (name, price, image_url) VALUES ('Product 3', 3000, 'http://example.com/product3.jpg');

-- Insert wishlist items
INSERT INTO wishlist (member_id, product_id) VALUES (
                      (SELECT id FROM member WHERE email = 'user1@example.com'),
                      (SELECT id FROM product WHERE name = 'Product 1')
                     );
INSERT INTO wishlist (member_id, product_id) VALUES (
                      (SELECT id FROM member WHERE email = 'user1@example.com'),
                      (SELECT id FROM product WHERE name = 'Product 2')
                     );
INSERT INTO wishlist (member_id, product_id) VALUES (
                      (SELECT id FROM member WHERE email = 'user2@example.com'),
                      (SELECT id FROM product WHERE name = 'Product 2')
                     );
INSERT INTO wishlist (member_id, product_id) VALUES (
                      (SELECT id FROM member WHERE email = 'user2@example.com'),
                      (SELECT id FROM product WHERE name = 'Product 3')
                     );
INSERT INTO wishlist (member_id, product_id) VALUES (
                      (SELECT id FROM member WHERE email = 'user3@example.com'),
                      (SELECT id FROM product WHERE name = 'Product 1')
                     );
INSERT INTO wishlist (member_id, product_id) VALUES (
                      (SELECT id FROM member WHERE email = 'user3@example.com'),
                      (SELECT id FROM product WHERE name = 'Product 3')
                     );