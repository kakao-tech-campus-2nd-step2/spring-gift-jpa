create table Products(
    id bigint auto_increment,
    name varchar(255),
    price bigint,
    imageUrl varchar(2000),
    primary key(id)
);

create table Users(
    id bigint auto_increment,
    email varchar(50),
    password varchar(50),
    primary key(id)
);

create table WishLists(
    id bigint auto_increment,
    email varchar(50),
    productId bigint,
    count int,
    primary key(id)
)