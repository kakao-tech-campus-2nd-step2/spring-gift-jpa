CREATE TABLE IF NOT EXISTS product (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255),
    price DOUBLE,
    imageUrl VARCHAR(255)
);
