DROP TABLE product IF EXISTS;
CREATE TABLE product(id SERIAL, name VARCHAR(255), price int, imageUrl varchar(255));


DROP TABLE option IF exists;
CREATE TABLE option (id int, option varchar(255));

DROP TABLE user_tb IF exists;
CREATE TABLE user_tb(id SERIAL, email varchar(255), password varchar(255));

DROP TABLE wishlist IF exists;
CREATE TABLE wishlist(user_id int, product_id int);

