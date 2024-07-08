package gift.repository;

import gift.domain.Product;
import gift.domain.Wish;
import gift.utils.error.ProductNotFoundException;
import gift.utils.error.WishListAddFailedException;
import java.util.List;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishsRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishsRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wish> UserRowMapper = (rs, rowNum) -> new Wish(
        rs.getString("email"),
        rs.getLong("product_id"),
        rs.getLong("quantity")
    );

    public boolean addToWishlist(Wish wish) {


        // 위시리스트에 추가
        String sql = "INSERT INTO wishlist (email, product_id , quantity) VALUES (?, ?, ?)";
        try {
            int rowsAffected = jdbcTemplate.update(sql, wish.getEmail(), wish.getProductId(), wish.getQuantity());
            return rowsAffected > 0;
        } catch (DataIntegrityViolationException e) {
            throw new WishListAddFailedException("Failed to add item to wishlist");
        }

    }

    public boolean removeFromWishlist(String email, Long productId) {
        String sql = "DELETE FROM wishlist WHERE email = ? AND product_id = ?";
        return jdbcTemplate.update(sql, email, productId) > 0;
    }

    public List<Wish> getWishlistProducts(String email) {
        String sql = "SELECT * FROM wishlist WHERE email = ?";
        return jdbcTemplate.query(sql, UserRowMapper, email);
    }

    public boolean isProductInWishlist(String email, Long productId) {
        String sql = "SELECT COUNT(*) FROM wishlist WHERE email = ? AND product_id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email, productId);
        return count > 0;
    }

    public boolean changeToWishlist(Wish wish) {
        String checkProductSql = "SELECT COUNT(*) FROM products WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkProductSql, Integer.class, wish.getProductId());
        if (count == 0) {
            throw new ProductNotFoundException("Product with id " + wish.getProductId() + " not found");
        }

        // 위시리스트에 추가
        String sql = "UPDATE wishlist SET (email, product_id , quantity) VALUES (?, ?, ?)";
        try {
            int rowsAffected = jdbcTemplate.update(sql, wish.getEmail(), wish.getProductId(), wish.getQuantity());
            return rowsAffected > 0;
        } catch (DataIntegrityViolationException e) {
            throw new WishListAddFailedException("Failed to change item to wishlist");
        }

    }

}
