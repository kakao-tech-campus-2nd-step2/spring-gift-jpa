INSERT INTO Product (name, price, imageUrl) VALUES ('커피', 100, 'https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg');
INSERT INTO Product (name, price, imageUrl) VALUES ('콜라', 100, 'https://img.danawa.com/prod_img/500000/059/749/img/13749059_1.jpg?_v=20220524145210');
INSERT INTO Product (name, price, imageUrl) VALUES ('몬스터', 200, 'https://img.danawa.com/prod_img/500000/658/896/img/17896658_1.jpg?_v=20220923092758');

INSERT INTO Users (email, password, isDelete) VALUES ('kakao1@kakao.com', '1234', 0);
INSERT INTO Users (email, password, isDelete) VALUES ('kakao2@kakao.com', 'qwer1234', 0);
INSERT INTO Users (email, password, isDelete) VALUES ('kakao3@kakao.com', 'qwer1234', 1);

INSERT INTO Wish (userId, productId) VALUES (1, 1);
INSERT INTO Wish (userId, productId) VALUES (1, 2);
INSERT INTO Wish (userId, productId) VALUES (2, 1);
INSERT INTO Wish (userId, productId) VALUES (2, 3);