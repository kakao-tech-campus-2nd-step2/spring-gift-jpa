-- 더미 데이터를 삽입할 sql파일

insert into products (product_id, name, price, image) values (1, 'dummy1', 1000, 'dummy1.png');
insert into products (product_id, name, price, image) values (2, 'dummy2', 1000, 'dummy2.png');
insert into products (product_id, name, price, image) values (3, 'dummy3', 1000, 'dummy3.png');

insert into users (user_id, email, password) values (1, 'luckyrkd@naver.com', 'aaaaa11111');

insert into wish_products (user_id, product_id, quantity) values (1, 1, 5)