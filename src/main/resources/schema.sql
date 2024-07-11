drop table if exists product;
create table product (
    id bigint auto_increment,
    name varchar(255),
    price int,
    image_url varchar(255),
    primary key (id)
    );

drop table if exists member;
create table member (
    id bigint auto_increment,
    email varchar(255),
    password varchar(255),
    primary key (id)
    );

drop table if exists wishlist;
create table wishlist (
    id bigint auto_increment,
    userid bigint,
    productid bigint,
    primary key (id)
    );