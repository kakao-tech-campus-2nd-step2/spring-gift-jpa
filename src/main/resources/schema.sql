create table product(
                        id long not null,
                        name varchar(255),
                        price int,
                        url varchar(255),
                        primary key(id)
);


create table member(
                       id varchar(255) not null,
                       name varchar(50),
                       email varchar(255) not null,
                       password varchar(255) not null,
                       role boolean,
                       primary key(id)
);

create table wishlist(
                         member_id varchar(255) not null,
                         product_id long not null
);

create table blacklist(
                          id long auto_increment primary key,
                          token varchar(255) not null
);
