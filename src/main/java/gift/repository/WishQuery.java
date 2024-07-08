package gift.repository;

public enum WishQuery {
    SELECT_ALL_WISHES_BY_MEMBERID("select member_id, product_name, count from wishes where member_id=?"),
    INSERT_WISH_BY_MEMBERID("insert into wishes (product_name, count, member_id) values (?, ?, ?)"),
    UPDATE_WISH_BY_MEMBERID_PRODUCTNAME("update wishes set count=? where member_id=? and product_name=?"),
    DELETE_WISH_BY_MEMBERID_PRODUCTNAME("delete from wishes where member_id=? and product_name=?");

    private final String query;

    WishQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
