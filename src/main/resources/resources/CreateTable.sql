DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS wishes;
DROP TABLE IF EXISTS tokens;

CREATE TABLE IF NOT EXISTS products
(
    id    BIGINT PRIMARY KEY AUTO_INCREMENT,
    name  VARCHAR(255),
    price BIGINT,
    url   VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    email    VARCHAR(255),
    password VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS wishes
(
    id         BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id BIGINT,
    user_id    BIGINT,
    FOREIGN KEY (product_id) REFERENCES products (id),
    FOREIGN KEY (user_id) REFERENCES users (id)
);

CREATE TABLE IF NOT EXISTS tokens
(
    id          BIGINT PRIMARY KEY AUTO_INCREMENT,
    token_value VARCHAR(255)
);
