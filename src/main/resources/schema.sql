CREATE TABLE member (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        email VARCHAR(255) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL
);
CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL UNIQUE ,
                         price BIGINT NOT NULL,
                         imageUrl VARCHAR(255)
);
CREATE TABLE wishlist (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          member_id BIGINT NOT NULL,
                          item_name VARCHAR(255) NOT NULL,
                          CONSTRAINT fk_member
                              FOREIGN KEY (member_id)
                                  REFERENCES member(id)
                                  ON DELETE CASCADE
);
CREATE TABLE tokenauth (
                           token VARCHAR(255) NOT NULL PRIMARY KEY,
                           email VARCHAR(255) NOT NULL,
                           CONSTRAINT fk_member_email
                                FOREIGN KEY (email)
                                REFERENCES member(email)
                                ON DELETE CASCADE
);
