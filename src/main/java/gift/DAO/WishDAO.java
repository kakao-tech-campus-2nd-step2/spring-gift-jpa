package gift.DAO;

import gift.Entity.WishEntity;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<WishEntity> rowMapper = new BeanPropertyRowMapper<>(WishEntity.class);

    public WishDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(WishEntity wish) {
        String sql = "INSERT INTO wishes (user_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, wish.getUserId(), wish.getProductId());
    }

    public List<WishEntity> findByUserId(Long userId) {
        String sql = "SELECT w.id, w.user_id, w.product_id, p.name AS product_name " +
                "FROM wishes w " +
                "JOIN product p ON w.product_id = p.id " +
                "WHERE w.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, rowMapper);
    }

    public void deleteByUserIdAndWishId(Long userId, Long wishId) {
        String sql = "DELETE FROM wishes WHERE user_id = ? AND id = ?";
        jdbcTemplate.update(sql, userId, wishId);
    }
}