INSERT INTO product (id, name, price, imageUrl, role) VALUES
(814607, '아이스 카페 아메리카노 T', 4500, 'https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg', "admin");

INSERT INTO users (id, name, password, email, role) VALUES
(1, 'kakao', 'password', 'abc@123', 'admin')

INSERT INTO wishlist(product_id, user_id) VALUES
(814607, 1) 