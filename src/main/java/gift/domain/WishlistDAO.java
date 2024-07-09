package gift.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistDAO {
    @Autowired
    private final JdbcTemplate jdbcTemplate;

    public WishlistDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create() {
        var sql = """
                create table wishlist (
                    token varchar(255) NOT NULL,
                    id bigint NOT NULL,
                    num bigint,
                    FOREIGN KEY (token) REFERENCES member(token),
                    FOREIGN KEY (id) REFERENCES product(id)
                )
                """;
        jdbcTemplate.execute(sql);
    }

    public long selectNumByItem(String token, long id) {
        var sql = "SELECT num FROM wishlist WHERE token = ? AND id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{token, id}, Long.class);
    }

    public void updateNum(String token, long id, long num) {
        var sql = "UPDATE wishlist SET num = ? WHERE token = ? AND id = ?";
        jdbcTemplate.update(sql, new Object[]{num, token, id});
    }

    public List<WishlistItem> selectAll(String token) {
        var sql = "SELECT * FROM wishlist WHERE token = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new WishlistItem(
                        rs.getString("token"),
                        rs.getLong("id"),
                        rs.getLong("num")
                ));
    }

    public void insert(String token, long id) {
        var num = selectNumByItem(token, id);
        var sql = "INSERT INTO wishlist (token, id, num) VALUES (?, ?, )";
        jdbcTemplate.update(sql, token, id, num + 1);
    }

    public void delete(String token, long id) {
        var sql = "DELETE FROM wishlist WHERE token = ? AND id = ?";
        jdbcTemplate.update(sql, token, id);
    }
}
