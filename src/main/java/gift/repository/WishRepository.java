package gift.repository;

import gift.dto.wish.WishResponse;
import gift.entity.Wish;
import gift.entity.Product;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;
    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WishResponse> findByUserId(Long userId) {
        String sql = "SELECT w.id as id, p.id as product_id, p.name, p.price, p.imageUrl, w.quantity "
                + "FROM wishes w "
                + "JOIN products p ON w.product_id = p.id "
                + "WHERE w.user_id = ?";

        try {
            return jdbcTemplate.query(sql, wishesRowMapper, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Long findWishId(Long userId, Long productId) {
        String sql = "SELECT id FROM wishes WHERE user_id = ? AND product_id = ?";

        try {
            return jdbcTemplate.queryForObject(sql, Long.class, userId, productId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<WishResponse> insert(Wish wish) {
        String sql = "INSERT INTO wishes (user_id, product_id, quantity) "
            + "VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, wish.userId(), wish.productId(), wish.quantity());

        return findByUserId(wish.userId());
    }

    public void update(Wish wish) {
        String sql = "UPDATE wishes SET quantity = ? WHERE id = ?";
        jdbcTemplate.update(sql, wish.quantity(), wish.id());
    }

    public void delete(Wish wish) {
        String sql = "DELETE FROM wishes WHERE id = ?";
        jdbcTemplate.update(sql, wish.id());
    }

    private final RowMapper<WishResponse> wishesRowMapper = (rs, rowNum) -> {
        Product product = new Product(
            rs.getLong("product_id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("imageUrl")
        );
        return new WishResponse(rs.getLong("id"), product, rs.getInt("quantity"));
    };
}
