-- for Product
insert into products(name, price, image_url) values ('아이스 카페 아메리카노 T', 4500, 'https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg');
insert into products(name, price, image_url) values ('제로 펩시 라임 355ml', 2300, 'https://img.danawa.com/prod_img/500000/193/555/img/13555193_1.jpg?shrink=330:*&_v=20230222093241');
insert into products(name, price, image_url) values ('오예스 12개입 360g', 3700, 'https://img.danawa.com/prod_img/500000/965/117/img/10117965_1.jpg?shrink=330:*&_v=20191210171250');
insert into products(name, price, image_url) values ('농심 육개장 사발면 소', 990, 'https://i.namu.wiki/i/ydm9GPPnZldqoMbVcl-pVaodrUu6VBedp_vyZnrnn2WrYBvESNYo1BB2g7cK_w8b2Mw-C66pRScUfEJT3sIMrw.webp');
insert into products(name, price, image_url) values ('바나나맛 우유 240ml', 1700, 'https://img.danawa.com/prod_img/500000/107/815/img/3815107_1.jpg?_v=20231212093346');

-- for User
insert into users(email, password, permission) values ('admin@example.com', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin'); -- plain password is 'admin'
insert into users(email, password, permission) values ('user@example.com', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 'user'); -- plain password is 'user';
insert into users(email, password, permission) values ('user2@example.com', '04f8996da763b7a969b1028ee3007569eaf3a635486ddab211d512c85b9df8fb', 'user'); -- plain password is 'user';


-- for Wishlist
insert into wishes(product_id, user_id, quantity) values (4, 2, 5);
insert into wishes(product_id, user_id, quantity) values (1, 2, 2);
insert into wishes(product_id, user_id, quantity) values (3, 3, 4);
insert into wishes(product_id, user_id, quantity) values (5, 3, 1);