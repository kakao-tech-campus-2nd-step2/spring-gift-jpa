DELETE FROM products;
ALTER TABLE products ALTER COLUMN id RESTART WITH 1;

INSERT INTO products (name, price, image_url) VALUES ('Product A', 1000, 'http://example.com/images/product_a.jpg');
INSERT INTO products (name, price, image_url) VALUES ('Product B', 2000, 'http://example.com/images/product_b.jpg');
INSERT INTO products (name, price, image_url) VALUES ('Product C', 3000, 'http://example.com/images/product_c.jpg');
INSERT INTO products (name, price, image_url) VALUES ('Product D', 4000, 'http://example.com/images/product_d.jpg');
INSERT INTO products (name, price, image_url) VALUES ('Product E', 5000, 'http://example.com/images/product_e.jpg');
