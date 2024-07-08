create table `products`
(
    `id`        bigint auto_increment primary key,
    `name`      varchar(255) not null,
    `price`     int          not null,
    `image_url` varchar(255) not null
);

create table `members`
(
    `id`       bigint auto_increment primary key,
    `name`     varchar(255) not null,
    `email`    varchar(255) not null unique,
    `password` varchar(255) not null,
    `role`     varchar(255) not null
);

create table `wishes`
(
    `id`         bigint auto_increment primary key,
    `member_id`  bigint not null,
    `product_id` bigint not null,
    `count`      int    not null,
    FOREIGN KEY (`member_id`) REFERENCES `members` (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
);