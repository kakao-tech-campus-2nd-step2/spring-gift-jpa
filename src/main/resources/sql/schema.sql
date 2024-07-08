drop table if exists product;
drop table if exists users;
drop table if exists user_product;

create table product (
                          id bigint auto_increment primary key,
                          name varchar(255) not null ,
                          price int not null ,
                          imageUrl varchar(255) not null
);

create table users (
                        id bigint auto_increment primary key,
                        password varchar(255) not null ,
                        email varchar(255) unique not null
);

create table user_product (
                             user_id bigint,
                             product_id bigint,
                             FOREIGN KEY (user_id) REFERENCES users(id),
                             FOREIGN KEY (product_id) REFERENCES product(id)
);
