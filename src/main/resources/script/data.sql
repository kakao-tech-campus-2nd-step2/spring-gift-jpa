INSERT INTO product (name, price, image_url, user_id)
VALUES ('상품 1', 100000, 'http://example.com/image1.jpg', 1),
       ('상품 2', 20000, 'http://example.com/image2.jpg', 1),
       ('상품 3', 1500, NULL, 1);

INSERT INTO app_user (email, password, role)
VALUES ('yoo@example.com', '1234', 'ADMIN'),
       ('kyeong@example.com', '1234', 'USER'),
       ('miiii@example.com', '1234', 'USER');

INSERT INTO wish (user_id, product_id, quantity)
VALUES (1, 1, 2);
INSERT INTO Wish (user_id, product_id, quantity)
VALUES (1, 2, 1);
INSERT INTO Wish (user_id, product_id, quantity)
VALUES (2, 3, 3);
INSERT INTO Wish (user_id, product_id, quantity)
VALUES (2, 1, 1);
INSERT INTO Wish (user_id, product_id, quantity)
VALUES (3, 1, 1);
