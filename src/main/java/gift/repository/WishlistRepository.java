package gift.repository;

import gift.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(Wishlist wishlist){
        String sql = "INSERT INTO wishlist(userId, productId) VALUES (?, ?)";
        jdbcTemplate.update(sql, wishlist.getUserId(), wishlist.getProductId());
    }

    public List<Wishlist> findAll(){
        String sql = "SELECT userId, productId FROM wishlist";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Wishlist(
                resultSet.getString("userId"),
                resultSet.getLong("productId")
        ));
    }

    public List<Wishlist> findByUserId(String userId) {
        String sql = "SELECT userId, productId FROM wishlist WHERE userId = ?";
        return jdbcTemplate.query(sql, new Object[]{userId}, (resultSet, rowNum) -> new Wishlist(
                resultSet.getString("userId"),
                resultSet.getLong("productId")
        ));
    }

    public void delete(String userId, Long productId){
        String sql = "DELETE FROM wishlist WHERE userId = ? AND productId = ?";
        jdbcTemplate.update(sql, userId, productId);
    }
}