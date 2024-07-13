INSERT INTO products (id, name, price, image_url) VALUES (1, 'Sample1', 1000, 'http://image1.jpg');
INSERT INTO products (id, name, price, image_url) VALUES (2, 'Sample2', 2000, 'http://image2.jpg');
INSERT INTO products (id, name, price, image_url) VALUES (3, 'Sample3', 3000, 'http://image3.jpg');
ALTER TABLE products ALTER COLUMN id RESTART WITH 4;

INSERT INTO members (id, email, password)
VALUES (1, 'admin1@kakao.com', 'MTExMQ=='); --원래 비밀번호 1111
INSERT INTO members (id, email, password)
VALUES (2, 'admin2@kakao.com', 'MjIyMg=='); --원래 비밀번호 2222
ALTER TABLE members ALTER COLUMN id RESTART WITH 3;

INSERT INTO wish_lists (id, product_id, member_id) VALUES (1, 1, 1);
INSERT INTO wish_lists (id, product_id, member_id) VALUES (2, 2, 1);
INSERT INTO wish_lists (id, product_id, member_id) VALUES (3, 1, 2);
INSERT INTO wish_lists (id, product_id, member_id) VALUES (4, 3, 2);
ALTER TABLE wish_lists ALTER COLUMN id RESTART WITH 5;