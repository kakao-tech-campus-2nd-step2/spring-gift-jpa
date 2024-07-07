create table product(
    id bigint,
    name varchar(255),
    price int,
    imageUrl varchar(255),
    primary key (id)
);

create table user_table(
    id bigint auto_increment,
    email varchar(255),
    password varchar(255),
    role varchar(31),
    primary key (id)
);