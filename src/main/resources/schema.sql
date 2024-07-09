create table products
(
    id       bigint auto_increment,
    name     varchar(255),
    price    bigint,
    imageUrl varchar(255),
    primary key (id)
);
create table members
(
    email varchar(30),
    password varchar(30),
    primary key (email)
);
create table wishes
(
    email          varchar(30),
    productId      bigint,
    count          bigint
);