CREATE TABLE IF NOT EXISTS wish_list_items (
                                               id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                               member_id BIGINT NOT NULL,
                                               product_id BIGINT NOT NULL,
                                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);