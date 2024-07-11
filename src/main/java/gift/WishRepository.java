package gift;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepository(@NonNull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class WishRowMapper implements RowMapper<Wish> {
        @Override
        public Wish mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            return new Wish(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
            );
        }
    }

    public List<Wish> findAllByMemberId(Long memberId) {
        return jdbcTemplate.query("SELECT * FROM wish WHERE member_id = ?", new Object[]{memberId}, new WishRowMapper());
    }

    public void save(@NonNull Wish wish) {
        jdbcTemplate.update("INSERT INTO wish (member_id, product_id) VALUES (?, ?)", wish.getMemberId(), wish.getProductId());
    }

    public void deleteByMemberIdAndProductId(Long memberId, Long productId) {
        jdbcTemplate.update("DELETE FROM wish WHERE member_id = ? AND product_id = ?", memberId, productId);
    }
}
