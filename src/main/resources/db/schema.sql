# drop table if exists products_tb CASCADE;
# create table products_tb
# (
#     id bigint AUTO_INCREMENT PRIMARY KEY,
#     name varchar(50),
#     price int,
#     imageUrl varchar(255)
# );
#
# drop table if exists user_tb CASCADE;
# create table user_tb
# (
#     id bigint AUTO_INCREMENT PRIMARY KEY,
#     email varchar(50),
#     password varchar(20),
#     accessToken varchar(255)
# );
#
# drop table if exists wishlist_tb CASCADE;
# CREATE TABLE wishlist_tb (
#     id BIGINT AUTO_INCREMENT PRIMARY KEY,
#     user_id BIGINT NOT NULL,
#     product_id BIGINT NOT NULL,
#     quantity INT NOT NULL,
#     FOREIGN KEY (user_id) REFERENCES user_tb(id),
#     FOREIGN KEY (product_id) REFERENCES products_tb(id)
# );