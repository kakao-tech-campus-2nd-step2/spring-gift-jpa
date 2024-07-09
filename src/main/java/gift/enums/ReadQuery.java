package gift.enums;

public enum ReadQuery {

    FIND_PRODUCT_BY_ID("select id, name, price, imageUrl from product where id = ?"),
    FIND_ALL_PRODUCT("select id, name, price, imageUrl from product"),
    FIND_USER_BY_EMAIL("select id, password, email from users where email = ?"),
    COUNT_USER_BY_EMAIL("select count(*) from users where email = ?"),
    FIND_ALL_WISH("select * from product join user_product on product.id = user_product.product_id where user_product.user_id = ?");

    private final String query;

    ReadQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
