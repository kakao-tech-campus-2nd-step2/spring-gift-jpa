package gift.model.wish;

import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class WishDao {

    private static final String SQL_INSERT = "INSERT INTO wishes (user_id, product_id, count) VALUES (?, ?, ?)";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM wishes WHERE id = ?";
    private static final String SQL_UPDATE_COUNT = "UPDATE wishes SET count = ? WHERE user_id = ? AND product_id = ?";
    private static final String SQL_SELECT_ALL = "SELECT * FROM wishes WHERE user_id = ?";
    private static final String SQL_SELECT_BY_ID = "SELECT * FROM wishes WHERE id = ?";
    private static final String SQL_SELECT_BY_PRODUCT_ID_AND_USER_ID = "SELECT * FROM wishes WHERE product_id = ? AND user_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Wish> wishRowMapper = new WishRowMapper();

    public WishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(Wish wish) {
        jdbcTemplate.update(SQL_INSERT, wish.getUserId(), wish.getProductId(), wish.getCount());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    public void updateCount(String userId, Long productId, Long count) {
        jdbcTemplate.update(SQL_UPDATE_COUNT, count, userId, productId);
    }

    public List<Wish> findAll(String userId) {
        return jdbcTemplate.query(SQL_SELECT_ALL, wishRowMapper, userId);
    }

    public Optional<Wish> findById(Long id) {
        try {
            Wish wish = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID, wishRowMapper, id);
            return Optional.of(wish);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Wish> findByProductIdAndUserId(Long productId, String userId) {
        try {
            Wish wish = jdbcTemplate.queryForObject(SQL_SELECT_BY_PRODUCT_ID_AND_USER_ID,
                wishRowMapper, productId, userId);
            return Optional.of(wish);
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
