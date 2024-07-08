drop table gift if exists;
drop table users if exists;
create table gift(
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `name` varchar(255) not null,
    `price` INT not null,
    `imageUrl` varchar(255) not null

);
CREATE TABLE users (
    `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
    `email` VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL
);
CREATE TABLE user_gifts (
    `user_id` BIGINT NOT NULL,
    `gift_id` BIGINT NOT NULL,
    `quantity` INT NOT NULL DEFAULT 1,
    PRIMARY KEY (user_id, gift_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (gift_id) REFERENCES gift(id)
);