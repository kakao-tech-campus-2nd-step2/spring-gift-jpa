INSERT INTO product (name, price, image_url)
VALUES ('아메리카노', 4500, 'https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111814_9a667f9eccc943648797925498bdd8a3.jpg'),
       ('딸기 딜라이트', 6300, 'https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111138_e4c94a012a594e0bb0cf89bae309f37f.jpg'),
       ('딸기 아사이', 5900, 'https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg');

INSERT INTO users (email, password, role)
VALUES ('user1@example.com', 'password1', 'ROLE_USER'),
       ('user2@example.com', 'password2', 'ROLE_USER'),
       ('user3@example.com', 'password3', 'ROLE_USER'),
       ('user4@example.com', 'password4', 'ROLE_USER'),
       ('user5@example.com', 'password5', 'ROLE_USER');

INSERT INTO wish (user_id, product_id)
VALUES ((SELECT id FROM users WHERE email = 'user1@example.com'),
        (SELECT id FROM product WHERE name = '딸기 딜라이트')),
       ((SELECT id FROM users WHERE email = 'user2@example.com'),
        (SELECT id FROM product WHERE name = '아메리카노')),
       ((SELECT id FROM users WHERE email = 'user3@example.com'),
        (SELECT id FROM product WHERE name = '딸기 아사이'));


