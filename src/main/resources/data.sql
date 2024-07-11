INSERT INTO Item (name, price, img_url)
values ('피자',
        13000,
        'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSwvPdrxYSEP7ZHfy9b3IzZ2yU2HkDh2BjSA_cHkth04jKFwTyQwxA26VlAlw&s');
INSERT INTO Item (name, price, img_url)
values ('샐러드',
        20000,
        'https://media.istockphoto.com/id/183752521/ko/%EC%82%AC%EC%A7%84/%EB%B9%84%EB%B9%84%EB%B0%A5.jpg?s=2048x2048&w=is&k=20&c=f5FX83thuFkWNXmo-k7q5zD3xoEdO9oTFBSPEzBxMR0=');

insert into users (email,password) values ('123@123','123');
insert into users (email,password) values ('test@test','test');
insert into users (email,password) values ('test2@test2','test');


insert into wish (user_id,item_id) values(1,1);
insert into wish (user_id,item_id) values(1,2);
insert into wish (user_id,item_id) values(2,1);
insert into wish (user_id,item_id) values(3,1);
