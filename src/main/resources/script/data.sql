INSERT INTO Product (name, price, image_url)
VALUES ('상품 1', 100000, 'http://example.com/image1.jpg'),
       ('상품 2', 20000, 'http://example.com/image2.jpg'),
       ('상품 3', 1500, NULL);

INSERT INTO AppUser (email, password, role)
VALUES ('yoo@example.com', '1234', 'ADMIN'),
       ('kyeong@example.com', '1234', 'USER'),
       ('miiii@example.com', '1234', 'USER');

INSERT INTO Wish (userId, productId, quantity)
VALUES (1, 1, 2);
INSERT INTO Wish (userId, productId, quantity)
VALUES (1, 2, 1);
INSERT INTO Wish (userId, productId, quantity, is_active)
VALUES (2, 3, 3, FALSE);
INSERT INTO Wish (userId, productId, quantity)
VALUES (2, 1, 1);
INSERT INTO Wish (userId, productId, quantity)
VALUES (3, 1, 1);
