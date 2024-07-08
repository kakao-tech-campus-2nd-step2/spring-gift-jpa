-- 기본 product 데이터 삽입
INSERT INTO product (name, price, image_url) VALUES ('아이스 아메리카노 T', 4500, 'https://example.com/image.jpg');
INSERT INTO product (name, price, image_url) VALUES ('아이스 카푸치노 M', 4700, 'https://example.com/image.jpg');
INSERT INTO product (name, price, image_url) VALUES ('핫 말차라떼 L', 6800, 'https://example.com/image.jpg');

-- 기본 users 데이터 삽입
INSERT INTO users (email, password) VALUES ('minji@example.com', 'password1');
INSERT INTO users (email, password) VALUES ('junseo@example.com', 'password2');
INSERT INTO users (email, password) VALUES ('donghyun@example.com', 'password3');

-- 기본 장바구니 데이터 삽입
INSERT INTO cart (user_id, product_id) VALUES (1, 3);
INSERT INTO cart (user_id, product_id) VALUES (2, 2);
INSERT INTO cart (user_id, product_id) VALUES (3, 2);
