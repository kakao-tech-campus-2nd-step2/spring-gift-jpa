package gift.enums;

public enum WriteQuery {

    UPDATE_PRODUCT("update product set name = ?, price = ?, imageUrl = ? where id = ?"),
    DELETE_PRODUCT("delete from product where id = ?"),
    DELETE_CONSTRAINT("DELETE FROM user_product WHERE product_id = ?"),
    REGISTER_WISH("INSERT INTO user_product (user_id, product_id) VALUES (?, ?)"),
    DELETE_WISH("DELETE FROM user_product WHERE user_id = ? AND product_id = ? LIMIT ?");

    private final String query;

    WriteQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
