create table products (
    id bigint auto_increment,
    name varchar(255),
    price bigint,
    image_url varchar(255),
    primary key (id)
);

create table members (
      id bigint auto_increment,
      email varchar(255),
      password varchar(255),
      role varchar(255) default 'user',
      primary key (id)
);

create table wishes (
    id bigint auto_increment,
    email varchar(255),
    productId bigint,
    count bigint,
    primary key (id)
);