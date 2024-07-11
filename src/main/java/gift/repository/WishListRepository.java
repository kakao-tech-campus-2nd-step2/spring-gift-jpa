package gift.repository;

import gift.domain.product.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findProductListbyMemberId(Long memberId) {
        String sql = "SELECT p.* FROM Wish w JOIN Product p ON w.product_id = p.id WHERE w.member_id = ?";

        return jdbcTemplate.query(sql,
            (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("price"),
                rs.getString("imageUrl")
            ),
            memberId
        );
    }

    public void insert(Long memberId, Long productId) {
        String sql = "INSERT INTO Wish (member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    public void delete(Long memberId, Long productId) {
        String sql = "DELETE FROM Wish WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }
}
