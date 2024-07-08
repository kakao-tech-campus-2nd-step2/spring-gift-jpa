package gift.repository;

public enum ProductQuery {
    SELECT_ALL_PRODUCT("select id, name, price, image_url from products"),
    SELECT_PRODUCT_BY_ID("select id, name, price, image_url from products where id=?"),
    INSERT_PRODUCT("insert into products (name, price, image_url) values (?, ?, ?)"),
    UPDATE_PRODUCT_BY_ID("update products set name=?, price=?, image_url=? where id=?"),
    DELETE_PRODUCT_BY_ID("DELETE FROM products where id=?");

    private final String query;

    ProductQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
