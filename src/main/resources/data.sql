INSERT INTO PRODUCTS(name,price,image_url) VALUES ('productA', 100, 'https://url1.com');
INSERT INTO PRODUCTS(name,price,image_url) VALUES ('productB', 5000, 'https://url2.com');
INSERT INTO PRODUCTS(name,price,image_url) VALUES ('productC', 500000, 'https://url3.com');
INSERT INTO PRODUCTS(name,price,image_url) VALUES ('productD', 1000000, 'https://url4.com');

INSERT INTO MEMBER(email,password,role) VALUES ('aaa123@a.com', '1234', 'ROLE_ADMIN');
INSERT INTO MEMBER(email,password,role) VALUES ('bbb123@b.com', '1234', 'ROLE_USER');
INSERT INTO MEMBER(email,password,role) VALUES ('ccc123@c.com', '1234', 'ROLE_USER');
INSERT INTO MEMBER(email,password,role) VALUES ('ddd123@d.com', '1234', 'ROLE_USER');

INSERT INTO WISH_PRODUCTS(member_id, product_id) VALUES (1, 1);
INSERT INTO WISH_PRODUCTS(member_id, product_id) VALUES (2, 2);
INSERT INTO WISH_PRODUCTS(member_id, product_id) VALUES (3, 3);
INSERT INTO WISH_PRODUCTS(member_id, product_id) VALUES (4, 4);