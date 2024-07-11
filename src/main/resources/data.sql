INSERT INTO product (name, price, imageUrl)
VALUES ('아메리카노', 4500,
        'https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111814_9a667f9eccc943648797925498bdd8a3.jpg'),
       ('딸기 딜라이트', 6300,
        'https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111138_e4c94a012a594e0bb0cf89bae309f37f.jpg'),
       ('딸기 아사이', 5900,
        'https://img1.kakaocdn.net/thumb/C320x320@2x.fwebp.q82/?fname=https%3A%2F%2Fst.kakaocdn.net%2Fproduct%2Fgift%2Fproduct%2F20231010111407_7fcb10e99eec4365af527f0bb3d27a0e.jpg');


INSERT INTO member (email, password)
VALUES ('user1@example.com', 'password1'),
       ('user2@example.com', 'password2'),
       ('user3@example.com', 'password3'),
       ('user4@example.com', 'password4'),
       ('user5@example.com', 'password5');

INSERT INTO wish (member_email, product_id)
VALUES ('user1@example.com', 2),
       ('user2@example.com', 1),
       ('user3@example.com', 3);
