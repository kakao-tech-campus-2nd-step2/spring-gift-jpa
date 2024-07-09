package gift.repository;

import gift.domain.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Wish wish) {
        String sql = "INSERT INTO wishes (member_id, product_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, wish.getMemberId(), wish.getProductName());
    }

    public boolean existsByMemberIdAndProductName(Long memberId, String productName) {
        String sql = "SELECT COUNT(*) FROM wishes WHERE member_id = ? AND product_name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{memberId, productName}, Integer.class);
        return count != null && count > 0;
    }

    public List<Wish> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM wishes WHERE member_id = ?";
        return jdbcTemplate.query(sql, new Object[]{memberId}, new WishRowMapper());
    }

    public void deleteByMemberIdAndProductName(Long memberId, String productName) {
        String sql = "DELETE FROM wishes WHERE member_id = ? AND product_name = ?";
        jdbcTemplate.update(sql, memberId, productName);
    }

    private static class WishRowMapper implements RowMapper<Wish> {
        @Override
        public Wish mapRow(ResultSet rs, int rowNum) throws SQLException {
            Wish wish = new Wish();
            wish.setId(rs.getLong("id"));
            wish.setMemberId(rs.getLong("member_id"));
            wish.setProductName(rs.getString("product_name"));
            return wish;
        }
    }
}
