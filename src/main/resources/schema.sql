create table if not exists member (
    id          BIGINT          not null AUTO_INCREMENT,
    email       VARCHAR(255)    not null,
    password    VARCHAR(255)    not null,
    PRIMARY KEY (id)
);

create table if not exists product (
    id          BIGINT          not null AUTO_INCREMENT,
    name        VARCHAR(15)     not null,
    price       INTEGER         not null,
    image_url   VARCHAR(255)    not null,
    PRIMARY KEY (id)
);

create table if not exists wish (
    id          BIGINT          not null AUTO_INCREMENT,
    member_id   BIGINT          not null,
    product_id  BIGINT          not null,
    PRIMARY KEY (id),
    FOREIGN KEY (member_id)     REFERENCES member(id),
    FOREIGN KEY (product_id)    REFERENCES product(id)
);

alter table member
    add constraint uk_member unique (email)