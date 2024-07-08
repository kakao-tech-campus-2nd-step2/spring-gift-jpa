INSERT INTO product (name, price, imageUrl) VALUES ('신라면', 1500, 'https://image.nongshim.com/non/pro/1647822565539.jpg');
INSERT INTO product (name, price, imageUrl) VALUES ('진라면', 1300, 'https://image.nongshim.com/non/pro/1647822565539.jpg');
INSERT INTO product (name, price, imageUrl) VALUES ('비빔면', 1200, 'https://image.nongshim.com/non/pro/1647822565539.jpg');
INSERT INTO product (name, price, imageUrl) VALUES ('카카오라면', 1100, 'https://image.nongshim.com/non/pro/1647822565539.jpg');

INSERT INTO member (email, password) VALUES ('user1@example.com', 'password1');
INSERT INTO member (email, password) VALUES ('user2@example.com', 'password2');


INSERT INTO wish (member_id, product_id) VALUES ((SELECT id FROM member WHERE email = 'user1@example.com'), (SELECT id FROM product WHERE name = '신라면'));