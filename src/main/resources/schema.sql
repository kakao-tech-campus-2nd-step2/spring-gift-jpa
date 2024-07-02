DROP TABLE IF EXISTS product;
create table product(
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(255),
    price bigint,
    imageUrl varchar(255),
    primary key (id)
)