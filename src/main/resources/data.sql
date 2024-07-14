INSERT INTO products (name, price, image_url) VALUES ('coffee', 4500, 'www.coffee.com');
INSERT INTO products (name, price, image_url) VALUES ('cake', 6500, 'www.cake.com');
INSERT INTO products (name, price, image_url) VALUES ('bread', 3000, 'www.bread.com');
INSERT INTO products (name, price, image_url) VALUES ('cookie', 2500, 'www.cookie.com');
INSERT INTO products (name, price, image_url) VALUES ('iceTea', 3000, 'www.iceTea.com');
INSERT INTO products (name, price, image_url) VALUES ('cledor', 2500, 'www.cledor.com');
INSERT INTO products (name, price, image_url) VALUES ('asinayo', 1500, 'www.asinayo.com');

INSERT INTO users (email, password, role) VALUES ('admin@admin', 'admin', 'admin');
INSERT INTO users (email, password, role) VALUES ('user@user', 'user', 'user');

INSERT INTO wish (user_id, product_id) VALUES (2, 1);
INSERT INTO wish (user_id, product_id) VALUES (2, 2);
INSERT INTO wish (user_id, product_id) VALUES (2, 3);
