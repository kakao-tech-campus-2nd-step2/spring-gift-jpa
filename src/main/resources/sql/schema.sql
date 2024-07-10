drop table if exists users CASCADE;
create table users
(
    id       bigint AUTO_INCREMENT PRIMARY KEY,
    email    varchar(50),
    password varchar(50)
);

drop table if exists wishlist CASCADE;
create table wishlist
(
    id        bigint AUTO_INCREMENT PRIMARY KEY,
    email     varchar(50),
    productId BIGINT
);

drop table if exists product CASCADE;
create table product
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    name        varchar(255),
    price       int,
    imageUrl    varchar(255),
    wishlist_id BIGINT,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist (id) ON DELETE CASCADE
);
