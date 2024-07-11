-- 스키마 구조를 미리 설정하는 sql파일

create table products
(
    product_id bigint,
    name       varchar(255),
    price      int,
    image      varchar(255),
    primary key (product_id)
);

create table users
(
    user_id  bigint,
    email    varchar(255) unique,
    password varchar(255),
    primary key (user_id)
);

-- 자주 사용할 product와 같은 데이터를 남겨서 성능 상으로 이득을 봐야 할지, 메모리를 아낄지 고민입니다.
create table wish_products
(
    user_id    bigint,
    product_id bigint,
    quantity   int,
    primary key (user_id, product_id),
    foreign key (user_id) references users (user_id),
    foreign key (product_id) references products (product_id)
);