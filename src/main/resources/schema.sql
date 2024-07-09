create table product
(
    id       bigint auto_increment,
    name     varchar(255),
    price    bigint,
    imageUrl varchar(255),
    primary key (id)
);
create table member
(
    email varchar(30),
    password varchar(30),
    primary key (email)
);
create table wish
(
    email          varchar(30),
    productId      bigint,
    count          bigint
);