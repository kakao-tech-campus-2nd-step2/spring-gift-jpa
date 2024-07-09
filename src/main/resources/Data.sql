-- Inserting initial data for products
INSERT INTO product (name, price, image_url)
VALUES ('Product A', 1000, 'image1.jpg');
INSERT INTO product (name, price, image_url)
VALUES ('Product B', 1500, 'image2.jpg');
INSERT INTO product (name, price, image_url)
VALUES ('Product C', 2000, 'image3.jpg');
INSERT INTO Product (name, price, image_url)
VALUES ('커피', 100,
        'https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg');
INSERT INTO Product (name, price, image_url)
VALUES ('콜라', 100,
        'https://img.danawa.com/prod_img/500000/059/749/img/13749059_1.jpg?_v=20220524145210');
INSERT INTO Product (name, price, image_url)
VALUES ('몬스터', 200,
        'https://img.danawa.com/prod_img/500000/658/896/img/17896658_1.jpg?_v=20220923092758');


-- Inserting initial data for users
INSERT INTO Users (email, password, is_delete)
VALUES ('kakao1@kakao.com', '1234', 0);
INSERT INTO Users (email, password, is_delete)
VALUES ('kakao2@kakao.com', '123456', 0);
INSERT INTO Users (email, password, is_delete)
VALUES ('kakao3@kakao.com', '1234', 0);
INSERT INTO Users (email, password, is_delete)
VALUES ('kakao4@kakao.com', 'qwer1234', 0);
INSERT INTO Users (email, password, is_delete)
VALUES ('kakao5@kakao.com', 'qwer1234', 1);

-- Wish 테이블에 데이터 삽입
INSERT INTO Wish (product_id, user_id)
VALUES (1, 1);
INSERT INTO Wish (product_id, user_id)
VALUES (2, 1);
INSERT INTO Wish (product_id, user_id)
VALUES (1, 2);
INSERT INTO Wish (product_id, user_id)
VALUES (3, 2);
