create table `products`
(
    `id`        int auto_increment primary key,
    `name`      varchar(255) not null,
    `price`     int          not null,
    `image_url` varchar(255) not null
);

create table `users`
(
    `id`       VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
    `name`     varchar(255) not null,
    `email`    varchar(255) not null unique,
    `password` varchar(255) not null,
    `role`     varchar(255) not null
);

create table `wishes`
(
    `id`         int auto_increment primary key,
    `user_id`    varchar(36) not null,
    `product_id` int         not null,
    `count`      int         not null,
    FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
    FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
);