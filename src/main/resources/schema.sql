DROP TABLE IF EXISTS product;
create table product(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    price bigint,
    imageUrl varchar(255),
    primary key (id)
)

DROP TABLE IF EXISTS user;
create table user(
    username varchar(255) PRIMARY KEY,
    pw varchar(255)
)