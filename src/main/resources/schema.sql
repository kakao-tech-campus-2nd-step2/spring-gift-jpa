
DROP TABLE IF EXISTS wishes;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price BIGINT NOT NULL,
                         description VARCHAR(255),
                         image_url VARCHAR(255)
);

CREATE TABLE wishes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        product_id BIGINT NOT NULL,
                        amount INT NOT NULL,
                        is_deleted BOOLEAN NOT NULL,
                        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                        CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
);


/*
CREATE TABLE product (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          price BIGINT NOT NULL,
                          description VARCHAR(255),
                          image_url VARCHAR(255)
);

CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS wishes (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      product_id BIGINT NOT NULL,
                                      user_id BIGINT NOT NULL,
                                      amount INT NOT NULL,
                                      is_deleted BOOLEAN NOT NULL
);

 */



/*
create table users
(
    id       bigint       not null auto_increment,
    email    varchar(255) not null,
    password varchar(255) not null,
    primary key (id)
) engine=InnoDB

create table product
(
    price     integer      not null,
    id        bigint       not null auto_increment,
    name      varchar(15)  not null,
    image_url varchar(255) not null,
    primary key (id)
) engine=InnoDB

create table wish
(
    id         bigint not null auto_increment,
    member_id  bigint not null,
    product_id bigint not null,
    primary key (id)
) engine=InnoDB

alter table member
    add constraint uk_member unique (email)


 */