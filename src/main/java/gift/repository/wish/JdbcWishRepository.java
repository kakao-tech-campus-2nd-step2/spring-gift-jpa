package gift.repository.wish;
/*
import gift.domain.wish.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcWishRepository implements WishRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcWishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Wish> wishRowMapper = (rs, rowNum) -> new Wish(
            rs.getLong("id"),
            rs.getLong("product_id"),
            rs.getLong("user_id"),
            rs.getInt("amount")
    );

    @Override
    public void save(Wish wish) {
        if (wish.isNew()) {
            String sql = "INSERT INTO wishes (product_id, user_id, amount, is_deleted) VALUES (?, ?, ?, ?)";
            jdbcTemplate.update(sql, wish.getProductId(), wish.getUserId(), wish.getAmount(), wish.isDeleted());
        } else {
            String sql = "UPDATE wishes SET product_id = ?, user_id = ?, amount = ?, is_deleted = ? WHERE id = ?";
            jdbcTemplate.update(sql, wish.getProductId(), wish.getUserId(), wish.getAmount(), wish.isDeleted(), wish.getId());
        }
    }

    @Override
    public Optional<Wish> findByIdAndUserId(Long wishId, Long userId) {
        String sql = "SELECT * FROM wishes WHERE id = ? AND user_id = ?";
        try {
            Wish wish = jdbcTemplate.queryForObject(sql, wishRowMapper, wishId, userId);
            return Optional.ofNullable(wish);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Wish> findByUserId(Long userId) {
        String sql = "SELECT * FROM wishes WHERE user_id = ? AND is_deleted = FALSE";
        try {
            List<Wish> wishes = jdbcTemplate.query(sql, wishRowMapper, userId);
            return wishes;
        } catch (Exception e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void deleteById(Long wishId) {
        String sql = "DELETE FROM wishes WHERE id = ?";
        jdbcTemplate.update(sql, wishId);
    }
}


 */