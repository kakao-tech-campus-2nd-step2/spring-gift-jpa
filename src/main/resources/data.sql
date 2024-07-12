-- product 초기 데이터
INSERT INTO product (id, name, price, imageUrl) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130004', 'pizza', 30000, 'pizzahot.com');
INSERT INTO product (id, name, price, imageUrl) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130005', 'hamburger', 5000, 'momstouch.com');
INSERT INTO product (id, name, price, imageUrl) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130006', 'chicken', 20000, 'bbq.com');
INSERT INTO product (id, name, price, imageUrl) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130007', 'hot dog', 2000, 'dogdoghotdog.com');
INSERT INTO product (id, name, price, imageUrl) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130008', 'ramen', 1500, 'menmenramen.com');

-- member 초기 데이터
INSERT INTO member (id, email, password, grade) VALUES ('1e8b9a6e-d7a4-11eb-b8bc-0242ac130003', 'test1', 'password1', 'admin');
INSERT INTO member (id, email, password, grade) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130009', 'test2', 'password2', 'user');

-- wish 초기 데이터
INSERT INTO wish (id, member_id, product_id, count) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130010', '1e8b9a6e-d7a4-11eb-b8bc-0242ac130003', '1e8b9c6e-d7a4-11eb-b8bc-0242ac130004', 1);
INSERT INTO wish (id, member_id, product_id, count) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130011', '1e8b9a6e-d7a4-11eb-b8bc-0242ac130003', '1e8b9c6e-d7a4-11eb-b8bc-0242ac130005', 2);
INSERT INTO wish (id, member_id, product_id, count) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130012', '1e8b9a6e-d7a4-11eb-b8bc-0242ac130003', '1e8b9c6e-d7a4-11eb-b8bc-0242ac130006', 1);
INSERT INTO wish (id, member_id, product_id, count) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130013', '1e8b9a6e-d7a4-11eb-b8bc-0242ac130003', '1e8b9c6e-d7a4-11eb-b8bc-0242ac130007', 3);
INSERT INTO wish (id, member_id, product_id, count) VALUES ('1e8b9c6e-d7a4-11eb-b8bc-0242ac130014', '1e8b9c6e-d7a4-11eb-b8bc-0242ac130009', '1e8b9c6e-d7a4-11eb-b8bc-0242ac130008', 1);
