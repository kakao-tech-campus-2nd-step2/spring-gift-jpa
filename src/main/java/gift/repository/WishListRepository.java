package gift.repository;

import gift.model.Product;
import gift.model.WishList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<Long> readWishList(Long userId) {
        String sql = "SELECT product_id FROM wishlist WHERE user_id = ?";
        return jdbcTemplate.queryForList(sql, Long.class, userId);
    }


    public void addProductToWishList(Long userId, Long productId) {
        String sql = "INSERT INTO wishlist (product_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, productId, userId);
    }

    public void removeWishList(Long userId) {
        String sql = "DELETE FROM wishlist WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void removeProductFromWishList(Long userId, Long productId) {
        String sql = "DELETE FROM wishlist WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, userId, productId);
    }

}
