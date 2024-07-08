package gift.dao;

import gift.domain.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WishDAO {

    private final JdbcTemplate jdbcTemplate;

    public WishDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> findByMemberId(Long memberId) {
        return jdbcTemplate.query(
                "SELECT * FROM wish WHERE member_id = ?",
                new Object[]{memberId},
                (rs, rowNum) -> mapRowToWish(rs)
        );
    }

    public Wish save(Wish wish) {
        jdbcTemplate.update(
                "INSERT INTO wish (member_id, product_name) VALUES (?, ?)",
                wish.getMemberId(), wish.getProductName()
        );
        return wish;
    }

    public void deleteById(Long wishId) {
        jdbcTemplate.update(
                "DELETE FROM wish WHERE id = ?",
                wishId
        );
    }

    private Wish mapRowToWish(ResultSet rs) throws SQLException {
        return new Wish(
                rs.getLong("id"),
                rs.getString("product_name"),
                rs.getLong("member_id")
        );
    }
}
