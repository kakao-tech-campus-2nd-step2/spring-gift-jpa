INSERT INTO product(name, price, image_url) VALUES ('Product 1', 100, 'https://via.placeholder.com/150');
INSERT INTO product(name, price, image_url) VALUES ('Product 2', 200, 'https://via.placeholder.com/250');
INSERT INTO product(name, price, image_url) VALUES ('Product 3', 300, 'https://via.placeholder.com/350');

INSERT INTO member(name, email, password) VALUES ('Member 1', 'member01@gmail.com', 'member010101');
INSERT INTO member(name, email, password) VALUES ('Member 2', 'member02@gmail.com', 'member020202');
INSERT INTO member(name, email, password) VALUES ('Member 3', 'member03@gmail.com', 'member030303');

INSERT INTO wish_product(member_id, product_id, quantity) VALUES (1, 1, 2);
INSERT INTO wish_product(member_id, product_id, quantity) VALUES (1, 2, 4);
INSERT INTO wish_product(member_id, product_id, quantity) VALUES (1, 3, 8);
INSERT INTO wish_product(member_id, product_id, quantity) VALUES (2, 2, 1);
