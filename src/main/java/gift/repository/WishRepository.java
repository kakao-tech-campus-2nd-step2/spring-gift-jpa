package gift.repository;

import gift.dto.WishlistResponseDTO;
import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String selectWishlistSql = "SELECT * FROM PRODUCTS p JOIN WISHLIST w ON p.id = w.product_id JOIN USERS u ON w.user_id = u.id WHERE u.id = ?";
    private static final String insertWishlistSql = "INSERT INTO WISHLIST (user_id, product_id) VALUES (?, ?)";
    private static final String deleteWishlistSql = "DELETE FROM WISHLIST WHERE user_id = ? AND product_id = ?";

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertWishProduct(Long userId, Long productId) {
        jdbcTemplate.update(insertWishlistSql, userId, productId);
    }

    public List<Product> selectWishlist(Long userId) {
        return jdbcTemplate.query(
                selectWishlistSql,
                new Object[]{userId},
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageurl")
                )
        );
    }

    public void deleteWishProduct(Long userId, Long productId) {
        jdbcTemplate.update(deleteWishlistSql, userId, productId);
    }
}
