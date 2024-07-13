-- 예시 상품 데이터
INSERT INTO product (name, price, image_url)
VALUES ('Product1', 1000, 'https://via.placeholder.com/150?text=product1'),
       ('Product2', 2000, 'https://via.placeholder.com/150?text=product2'),
       ('Product3', 3000, 'https://via.placeholder.com/150?text=product3'),
       ('Product4', 4000, 'https://via.placeholder.com/150?text=product4'),
       ('Product5', 5000, 'https://via.placeholder.com/150?text=product5'),
       ('Product6', 6000, 'https://via.placeholder.com/150?text=product6'),
       ('Product7', 7000, 'https://via.placeholder.com/150?text=product7'),
       ('Product8', 8000, 'https://via.placeholder.com/150?text=product8'),
       ('Product9', 9000, 'https://via.placeholder.com/150?text=product9'),
       ('Product10', 10000, 'https://via.placeholder.com/150?text=product10'),
       ('Product11', 11000, 'https://via.placeholder.com/150?text=product11');


-- 예시 회원 데이터
INSERT INTO member (email, password)
VALUES ('cussle@kakao.com', 'admin'),
       ('tester@kakao.com', 'test');

-- 예시 위시 리스트 데이터
INSERT INTO wish (member_id, product_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (2, 6),
       (2, 7),
       (2, 10),
       (2, 11);
