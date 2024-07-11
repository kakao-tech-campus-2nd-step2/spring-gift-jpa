INSERT INTO product (name, price, imageurl) VALUES ('신라면', 1500, 'https://image.nongshim.com/non/pro/1647822565539.jpg');
INSERT INTO product (name, price, imageurl) VALUES ('진라면', 1300, 'https://i.namu.wiki/i/GCCJ3FelPiCrZI5kB2Sm_EChm9x1Yv5BI8D63z020UXkVNVs8E3ucCvhiY_97yXAYRr3zd_tpa0rP1deo-gZUA.webp');
INSERT INTO product (name, price, imageurl) VALUES ('비빔면', 1200, 'https://i.namu.wiki/i/3XSXZdNeUEjycoiU_VcnA3ep1oe7UH7r69YdCDcFkPVvJTQBEY_WWSXpBZ8SDjRcyX3mEQsDmzB0xMof4G89Lg.webp');
INSERT INTO product (name, price, imageurl) VALUES ('카카오라면', 1100, 'https://i.namu.wiki/i/V3af5EZyVp6iEszBOsFtbdlJixznvd5gd35gmTNNHQCSPCcIdo2qixzNv9LrC_rVF6FTErWMghOFNrLLFExtVw.webp');
INSERT INTO product (name, price, imageurl) VALUES ('카카라면', 1100, 'https://st.kakaocdn.net/shoppingstore/store/20240528180123_b9a6145839c346e3a34304f14163d941.png');
INSERT INTO product (name, price, imageurl) VALUES ('카라면', 1100, 'https://t1.kakaocdn.net/thumb/C630x354.fwebp.q100/?fname=https%3A%2F%2Ft1.kakaocdn.net%2Fkakaocorp%2Fkakaocorp%2Fadmin%2Fnews%2F79590191017a00001.jpg');

INSERT INTO member (email, password) VALUES ('user1@example.com', 'password1');
INSERT INTO member (email, password) VALUES ('user2@example.com', 'password2');


INSERT INTO wish (member_id, product_id) VALUES ((SELECT id FROM member WHERE email = 'user1@example.com'), (SELECT id FROM product WHERE name = '신라면'));