package gift.repository;

import gift.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wishlist> wishlistRowMapper = (rs, rowNum) -> new Wishlist(
        rs.getLong("id"),
        rs.getString("user_email"),
        rs.getLong("product_id")
    );

    public List<Wishlist> getWishlist(String email) {
        String sql = "SELECT * FROM wishlists WHERE user_email = ?";
        return jdbcTemplate.query(sql, wishlistRowMapper, email);
    }

    public void addWishlist(String email, Long productId) {
        String sql = "INSERT INTO wishlists (user_email, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, email, productId);
    }

    public void removeWishlist(String email, Long productId) {
        String sql = "DELETE FROM wishlists WHERE user_email = ? AND product_id = ?";
        jdbcTemplate.update(sql, email, productId);
    }
}