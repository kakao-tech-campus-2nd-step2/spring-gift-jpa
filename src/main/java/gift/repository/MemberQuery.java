package gift.repository;

public enum MemberQuery {
    SELECT_ALL_MEMBER("select id, email, password, name, role from members"),
    SELECT_MEMBER_BY_EMAIL("select id, email, password, name, role from members where email=?"),
    SELECT_MEMBER_BY_ID("select id, email, password, name, role from members where id=?"),
    INSERT_MEMBER("insert into members (email, password, name, role) values (?, ?, ?, ?)"),
    UPDATE_MEMBER_BY_EMAIL("update members set email=?, password=?, name=?, role=? where email=?"),
    DELETE_MEMBER_BY_EMAIL("delete from members where email=?");
    private final String query;

    MemberQuery(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
