package gift.main.repository;

import gift.main.dto.WishListProductDto;
import gift.main.entity.WishlistProduct;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistProductDao {
    private final JdbcTemplate jdbcTemplate;

    public WishlistProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertWishlistProduct(WishListProductDto wishListProductDto) {
        String sql = "INSERT INTO wishlist_products (product_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, wishListProductDto.getProductId(), wishListProductDto.getUserId());
    }

    public List<WishlistProduct> selectWishlistProductsByUserId(Long userId) {
        String sql = "SELECT * FROM wishlist_products WHERE user_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new WishlistProduct(
                rs.getLong("id"),
                rs.getLong("product_id"),
                rs.getLong("user_id")
        ), userId);
    }

    public void deleteWishlistProductById(Long id) {
        String sql = "DELETE FROM wishlist_products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void deleteWishlistProductByUserIdAndProductId(Long userId, Long productId) {
        String sql = "DELETE FROM wishlist_products WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, userId, productId);
    }

}