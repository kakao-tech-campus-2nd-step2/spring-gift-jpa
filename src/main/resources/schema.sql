CREATE TABLE IF NOT EXISTS product
(
    id
    UUID
    PRIMARY
    KEY,
    name
    VARCHAR
(
    255
) NOT NULL, price BIGINT NOT NULL, imageUrl VARCHAR
(
    255
) );

CREATE TABLE IF NOT EXISTS member
(
    id
    UUID
    PRIMARY
    KEY,
    email
    VARCHAR
(
    255
) NOT NULL UNIQUE, password VARCHAR
(
    255
) NOT NULL, grade VARCHAR
(
    255
) );

CREATE TABLE IF NOT EXISTS wish
(
    id
    UUID
    PRIMARY
    KEY,
    member_id
    UUID,
    product_id
    UUID,
    count
    BIGINT
    NOT
    NULL,
    CONSTRAINT
    fk_member
    FOREIGN
    KEY
(
    member_id
) REFERENCES member
(
    id
), CONSTRAINT fk_product FOREIGN KEY
(
    product_id
) REFERENCES product
(
    id
) );
