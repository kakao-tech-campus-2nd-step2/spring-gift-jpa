package gift.repository.wish;

import gift.domain.User;
import gift.domain.WishList;
import gift.service.WishListService;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbcTemplate;
    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<WishList> wishRowMapper = (rs, rowNum) -> new WishList(
        rs.getLong("id"),
        rs.getLong("userId"),
        rs.getLong("productId"),
        rs.getInt("quantity")
    );
    public List<WishList> findByUserId(Long userId) {
        String sql = "select * from wishlist where userId = ?";
        return jdbcTemplate.query(sql,wishRowMapper, userId);
    }

    public void save(WishList wishList) {
        var sql = "insert into wishlist (userId, productId, quantity) values (?, ?, ?)";
        jdbcTemplate.update(sql, wishList.getUserId(), wishList.getProductId(), wishList.getQuantity());
    }

    public void updateQuantity(Long id, int quantity) {
        var sql = "update wishlist set quantity = ? where id = ?";
        jdbcTemplate.update(sql, quantity, id);
    }

    public void deleteById(Long id) {
        var sql = "delete from wishlist where id = ?";
        jdbcTemplate.update(sql, id);
    }


}
