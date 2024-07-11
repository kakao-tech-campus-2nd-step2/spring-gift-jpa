INSERT INTO Product (id, name, price, temperatureOption, cupOption, sizeOption, imageurl)
VALUES (1, '아이스 아메리카노', 2500, 'Ice', '일회용컵', 'tall', 'https://img.danawa.com/prod_img/500000/609/014/img/3014609_1.jpg?shrink=500:*&_v=20240510092724');

ALTER TABLE Product ALTER COLUMN id RESTART WITH 2;
