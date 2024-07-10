insert into member(email, password, role) values ('admin', '1234', 'ADMIN');

insert into product(name, price, image_url) values('lion', 1000, 'lion.jpg');
insert into product(name, price, image_url) values('appeach', 2000, 'appeach.jpg');
insert into product(name, price, image_url) values('chunsik', 3000, 'chunsik.jpg');
insert into product(name, price, image_url) values('jay_z', 1, 'jay_z.jpg');

insert into wish(member_id, product_id) values(1,1);
insert into wish(member_id, product_id) values(1,2);
insert into wish(member_id, product_id) values(1,3);
insert into wish(member_id, product_id) values(1,4);
