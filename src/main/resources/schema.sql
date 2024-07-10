drop table if exists product;
create table product
(
    price     integer      not null,
    id        bigint generated by default as identity,
    name      varchar(15)  not null,
    image_url varchar(255) not null,
    primary key (id)
)

drop table if exists member;
create table member
(
    id       bigint generated by default as identity,
    email    varchar(255) not null unique,
    password varchar(255) not null,
    primary key (id)
)
drop table if exists wish;
create table wish
(
    id         bigint generated by default as identity,
    member_id  bigint not null,
    product_id bigint not null,
    primary key (id)
)