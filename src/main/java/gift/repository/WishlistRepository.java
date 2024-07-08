package gift.repository;

import gift.domain.Wishlist;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wishlist> findWishList(Long userId) {
        String sql = "select * from wishlist where userId = ?";
        return jdbcTemplate.query(sql, wishlistRowMapper(), userId);
    }

    public int addWishItem(Wishlist wishlist) {
        String sql = "insert into wishlist (userId, productId, quantity) values (?, ?, ?)";
        return jdbcTemplate.update(sql, wishlist.getUserId(), wishlist.getProductId(), wishlist.getQuantity());
    }

    public int updateWishlistItem(Wishlist wishlist) {
        String sql = "UPDATE wishlist SET quantity = ? WHERE userId = ? AND productId = ?";
        return jdbcTemplate.update(sql, wishlist.getQuantity(), wishlist.getUserId(), wishlist.getProductId());
    }

    public int deleteWishItem(Long userId, Long productId) {
        String sql = "delete from wishlist where userId = ? and productId = ?";
        return jdbcTemplate.update(sql, userId, productId);
    }

    private RowMapper<Wishlist> wishlistRowMapper() {
        return (rs, rowNum) -> new Wishlist(
            rs.getLong("userId"),
            rs.getLong("productId"),
            rs.getInt("quantity")
        );
    }


}
