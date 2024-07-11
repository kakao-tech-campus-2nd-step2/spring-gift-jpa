CREATE TABLE Product
(
    id          BIGINT            AUTO_INCREMENT   PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    price       BIGINT         NOT NULL         CHECK (0 <= price),
    imageUrl    VARCHAR(255)   NOT NULL
);

create table Member
(
    id          BIGINT            AUTO_INCREMENT   PRIMARY KEY,
    email       VARCHAR(255)    NOT NULL        UNIQUE,
    password    VARCHAR(255)    NOT NULL
);

create table Wish
(
    id          BIGINT            AUTO_INCREMENT   PRIMARY KEY,
    member_id   BIGINT          NOT NULL,
    product_id  BIGINT          NOT NULL
);
