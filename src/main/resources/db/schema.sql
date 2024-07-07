CREATE TABLE Product
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    imageUrl VARCHAR(255) NOT NULL
);

CREATE TABLE Users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE WishList
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE WishList_Product
(
    wishlist_id BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    PRIMARY KEY (wishlist_id, product_id),
    FOREIGN KEY (wishlist_id) REFERENCES WishList (id),
    FOREIGN KEY (product_id) REFERENCES Product (id)
);
