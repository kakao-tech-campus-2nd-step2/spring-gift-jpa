package gift.repository;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {

    private JdbcTemplate jdbcTemplate;

    private static final String SELECT_ALL_PRODUCTS_BY_EMAIL_SQL
        = "SELECT product_id FROM wishlist WHERE email = ?";
    private static final String INSERT_PRODUCT_TO_WISHLIST_SQL
        = "INSERT INTO wishlist (email, product_id) VALUES (?, ?)";
    private static final String DELETE_PRODUCT_FROM_WISHLIST_SQL
        = "DELETE FROM wishlist WHERE email = ? AND product_id = ?";

    @Autowired
    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Long> findProductIdsByUserEmail(String email) {
        return jdbcTemplate.query(
            SELECT_ALL_PRODUCTS_BY_EMAIL_SQL,
            new Object[]{email},
            (rs, rowNum) -> rs.getLong("product_id")
        );
    }

    public void addProductToWishlist(String email, Long productId) {
        jdbcTemplate.update(INSERT_PRODUCT_TO_WISHLIST_SQL, email, productId);
    }

    public void removeProductFromWishlist(String email, Long productId) {
        jdbcTemplate.update(DELETE_PRODUCT_FROM_WISHLIST_SQL, email, productId);
    }
}
