-- 예시 상품 데이터
INSERT INTO products (name, price, imageURL) VALUES ('Product1', 1000, 'https://via.placeholder.com/150?text=product1');
INSERT INTO products (name, price, imageURL) VALUES ('Product2', 2000, 'https://via.placeholder.com/150?text=product2');

-- 예시 회원 데이터
INSERT INTO members (email, password) VALUES ('cussle@kakao.com', 'admin');
INSERT INTO members (email, password) VALUES ('tester@kakao.com', 'test');

-- 예시 위시 리스트 데이터
INSERT INTO wishlist (member_id, product_id) VALUES (1, 1);
INSERT INTO wishlist (member_id, product_id) VALUES (1, 2);
