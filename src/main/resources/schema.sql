DROP TABLE products IF EXISTS;

CREATE TABLE products(
                id BIGINT AUTO_INCREMENT,
                name VARCHAR(255),
                price INT,
                image_url VARCHAR(255),
                PRIMARY KEY (id)
            );


DROP TABLE members IF EXISTS;

CREATE TABLE members(
                id BIGINT AUTO_INCREMENT,
                email VARCHAR(255),
                password VARCHAR(255),
                name VARCHAR(255),
                role VARCHAR(255),
                PRIMARY KEY (id)
            );


DROP TABLE wishes IF EXISTS;

CREATE TABLE wishes(
                product_name VARCHAR(255),
                count VARCHAR(255),
                member_id BIGINT
            );
