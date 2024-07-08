DROP TABLE IF EXISTS PRODUCTS;
DROP TABLE IF EXISTS USERS;
DROP TABLE IF EXISTS WISHLISTS;

CREATE TABLE PRODUCTS (
                    id long NOT NULL AUTO_INCREMENT,
                    name varchar(255) NOT NULL,
                    price long NOT NULL,
                    imageurl varchar(255),
                    primary key (id)
                );


CREATE TABLE USERS (
                    id long NOT NULL AUTO_INCREMENT,
                    email varchar(255) NOT NULL,
                    password varchar(255) NOT NULL,
                    nickname varchar(255) NOT NULL,
                    primary key (id)
                );

CREATE TABLE WISHLISTS (
                    id long NOT NULL AUTO_INCREMENT,
                    userID long NOT NULL,
                    productID long NOT NULL,
                    count long NOT NULL,
                    primary key (id)
                );
