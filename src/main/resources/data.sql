INSERT INTO product (name, price, description, image_url) VALUES
                                                              ('Sample Product 1', 10000, 'Sample Description 1', 'c3JjL21haW4vcmVzb3VyY2VzL2ltYWdlU3RvcmFnZS90ZXN0LmpwZw'),
                                                              ('Sample Product 2', 20000, 'Sample Description 2', 'c3JjL21haW4vcmVzb3VyY2VzL2ltYWdlU3RvcmFnZS90ZXN0LmpwZw');

INSERT INTO users (email, password) VALUES
    ('pjhcsols@naver.com', '$2a$10$ENYqGvZ3p6LvtsBnRWINSOJHKlMt1Ykgb3.jCnoKkrhMihviXhkDu');

INSERT INTO wishes (product_id, user_id, amount, is_deleted) VALUES
                                                                 (1, 1, 5, FALSE),
                                                                 (2, 1, 3, FALSE);




/*
INSERT INTO product (name, price, description, image_url) VALUES
    ('Sample Product', 10000, 'Sample Description', 'c3JjL21haW4vcmVzb3VyY2VzL2ltYWdlU3RvcmFnZS90ZXN0LmpwZw');


INSERT INTO users (email, password) VALUES ('pjhcsols@naver.com', '$2a$10$ENYqGvZ3p6LvtsBnRWINSOJHKlMt1Ykgb3.jCnoKkrhMihviXhkDu'); -- password is '1q2w3e4r!'

INSERT INTO wishes (product_id, user_id, amount, is_deleted) VALUES (1, 1, 5, FALSE);
INSERT INTO wishes (product_id, user_id, amount, is_deleted) VALUES (2, 1, 3, FALSE);




 */