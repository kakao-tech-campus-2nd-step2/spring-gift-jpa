DROP TABLE IF EXISTS wish_list_products;
DROP TABLE IF EXISTS wish_lists;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
                       id BIGINT PRIMARY KEY,
                       email VARCHAR(255) UNIQUE,
                       password VARCHAR(255)
);

CREATE TABLE products (
                          id BIGINT PRIMARY KEY,
                          name VARCHAR(255),
                          price INT,
                          image_url VARCHAR(255)
);

CREATE TABLE wish_lists (
                            id BIGINT PRIMARY KEY,
                            user_id BIGINT,
                            FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE wish_list_products (
                                    wish_list_id BIGINT,
                                    product_id BIGINT,
                                    FOREIGN KEY (wish_list_id) REFERENCES wish_lists(id),
                                    FOREIGN KEY (product_id) REFERENCES products(id),
                                    PRIMARY KEY (wish_list_id, product_id)
);