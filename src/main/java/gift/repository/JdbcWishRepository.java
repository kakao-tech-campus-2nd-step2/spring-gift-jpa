package gift.repository;

import gift.domain.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcWishRepository implements WishRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcWishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("wish_list")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Wish save(Wish wish) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", wish.getUserId());
        parameters.put("product_id", wish.getProductId());
        parameters.put("created_at", wish.getCreatedAt());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        wish.setId(newId.longValue());

        return wish;
    }

    @Override
    public List<Wish> findByUserId(Long memberId) {
        String sql = "SELECT * FROM wish_list WHERE user_id = ?";
        return jdbcTemplate.query(sql, new WishRowMapper(), memberId);
    }

    @Override
    public void deleteByUserIdAndProductId(Long userId, Long productId) {
        String sql = "DELETE FROM wish_list WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, userId, productId);
    }

    private static class WishRowMapper implements RowMapper<Wish> {
        @Override
        public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
            Wish wish = new Wish();
            wish.setId(rs.getLong("id"));
            wish.setUserId(rs.getLong("user_id"));
            wish.setProductId(rs.getLong("product_id"));
            wish.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
            return wish;
        }
    }
}
