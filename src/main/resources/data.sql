INSERT INTO products (name, price, image_url) VALUES ('Sample1', 1000, 'http://image1.jpg');
INSERT INTO products (name, price, image_url) VALUES ('Sample2', 2000, 'http://image2.jpg');
INSERT INTO products (name, price, image_url) VALUES ('Sample3', 3000, 'http://image3.jpg');

INSERT INTO members (email, password)
VALUES ('admin1@kakao.com', 'MTExMQ=='); --원래 비밀번호 1111
INSERT INTO members (email, password)
VALUES ('admin2@kakao.com', 'MjIyMg=='); --원래 비밀번호 2222

INSERT INTO wish_lists (product_id, member_id) VALUES (1, 1);
INSERT INTO wish_lists (product_id, member_id) VALUES (2, 1);
INSERT INTO wish_lists (product_id, member_id) VALUES (1, 2);
INSERT INTO wish_lists (product_id, member_id) VALUES (3, 2);