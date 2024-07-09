package gift.repository;

import gift.domain.Wishlist;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository

public class WishlistDao {
    private final JdbcTemplate jdbcTemplate;

    public WishlistDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wishlist> wishlistRowMapper() {
        return (rs, rowNum) -> new Wishlist(
            rs.getLong("memberId"),
            rs.getLong("productId"),
            rs.getInt("quantity")
        );
    }

    public List<Wishlist> findWishListByMemberId(Long memberId) {
        String sql = "SELECT * FROM wishlist where memberId = ?";
        return jdbcTemplate.query(sql, wishlistRowMapper(), memberId);
    }

    public void addWishItem(Wishlist wishlist) {
        String sql = "INSERT INTO withlist (memberId, productId, quantity) values (?, ?, ?)";
        jdbcTemplate.update(sql, wishlist.getMemberId(), wishlist.getProductId(), wishlist);
    }

    public void updateWishItemQuanttity(Wishlist wishlist) {
        String sql = "UPDATE wishlist SET quantity = ? WHERE memberId = ? AND productId = ?";
        jdbcTemplate.update(sql, wishlist.getQuantity(), wishlist.getMemberId(), wishlist.getProductId());
    }

    public void deleteWishItem(Long memberId, Long productId) {
        String sql = "DELETE FROM wishlist WHERE userId = ? AND productId = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }
}
