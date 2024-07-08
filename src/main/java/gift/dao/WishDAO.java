package gift.dao;

import gift.entity.Wish;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishDAO {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Wish> rowMapper = new BeanPropertyRowMapper<>(Wish.class);

    public WishDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Wish wish) {
        String sql = "INSERT INTO wishes (user_id, product_id, number) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, wish.getUserId(), wish.getProductId(), wish.getNumber());
    }

    public List<Wish> findByUserId(Long userId) {
        String sql = "SELECT w.id, w.user_id, w.product_id, w.number, p.name AS product_name " +
            "FROM wishes w " +
            "JOIN product p ON w.product_id = p.id " +
            "WHERE w.user_id = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, rowMapper);
    }

    public Wish findByUserIdAndWishId(Long userId, Long wishId) {
        String sql = "SELECT w.id, w.user_id, w.product_id, w.number, p.name AS product_name " +
            "FROM wishes w " +
            "JOIN product p ON w.product_id = p.id " +
            "WHERE w.user_id = ? AND w.id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{userId, wishId}, rowMapper);
    }

    public void deleteByUserIdAndWishId(Long userId, Long wishId) {
        String sql = "DELETE FROM wishes WHERE user_id = ? AND id = ?";
        jdbcTemplate.update(sql, userId, wishId);
    }

    public void updateWishNumber(Long userId, Long wishId, int number) {
        String sql = "UPDATE wishes SET number = ? WHERE user_id = ? AND id = ?";
        jdbcTemplate.update(sql, number, userId, wishId);
    }
}
