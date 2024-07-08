drop table if exists product cascade;
drop table if exists product_option cascade;
drop table if exists member cascade;
drop table if exists wish_product cascade;

create table product
(
    id        bigint auto_increment not null,
    name      varchar(255) not null,
    price     int          not null,
    image_url varchar(255) not null,
    primary key (id)
);

create table product_option
(
    id               bigint auto_increment not null,
    product_id       bigint       not null,
    name             varchar(255) not null,
    additional_price int          not null,
    primary key (id),
    foreign key (product_id) references product (id)
);

create table member
(
    id       bigint auto_increment not null,
    name     varchar(255) not null,
    email    varchar(255) not null unique,
    password varchar(255) not null,
    role     varchar(255) not null,
    primary key (id)
);

create table wish_product
(
    id         bigint auto_increment not null,
    product_id bigint not null,
    member_id  bigint not null,
    count      int    not null,
    primary key (id),
    foreign key (product_id) references product (id),
    foreign key (member_id) references member (id)
);

insert into product(name, price, image_url)
values ('Apple 정품 아이폰 15',1700000,'https://lh5.googleusercontent.com/proxy/M33I-cZvIHdtsY_uyd5R-4KXJ8uZBBAgVw4bmZagF1T5krxkC6AHpxPUvU_02yDsRljgOHwa-cUTlhgYG_bSNJbbmnf6k9OOPRQyvPf5m4nD');
insert into product(name, price, image_url)
values ('Apple 정품 2024 아이패드 에어 11 M2칩',900000,'https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcThcspVP4EUYTEiUD0udG3dzUZDZOQH9eopFO7_7zZmIafSouktNeyQn8jzKwYTMxcQwaWN_iglo8LAus6DJTG_ogEaU_tHSOtNL3wiYJhYqisdTuMRT2o97h503C6gWd9BxV8_ow&usqp=CAc');
insert into product(name, price, image_url)
values ('샤오미 레드미 워치 3 액티브',40000,'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTTZbL2ZmDIRy5XrBugINb5bw33S7uia1CXjg&s');

insert into member(name, email, password, role)
values ('관리자', 'admin@naver.com', 'password', 'ADMIN');
insert into member(name, email, password, role)
values ('멤버', 'member@naver.com', 'password', 'MEMBER');
