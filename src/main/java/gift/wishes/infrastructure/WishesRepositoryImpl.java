package gift.wishes.infrastructure;

import gift.core.domain.product.Product;
import gift.core.domain.wishes.WishesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@Repository
public class WishesRepositoryImpl implements WishesRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WishesRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void saveWish(Long userId, Long productId) {
        String sql = "INSERT INTO wishes (user_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, userId, productId);
    }

    @Override
    public void removeWish(Long userId, Long productId) {
        String sql = "DELETE FROM wishes WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, userId, productId);
    }

    @Override
    public boolean exists(Long userId, Long productId) {
        String sql = "SELECT EXISTS(SELECT 1 FROM wishes WHERE user_id = ? AND product_id = ?)";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, userId, productId);
        return result != null && result;
    }

    @Override
    public List<Product> getWishlistOfUser(Long userId) {
        String sql = """
                SELECT p.id, p.name, p.price, p.image_url
                FROM products p
                LEFT JOIN wishes w ON p.id = w.product_id
                WHERE w.user_id = ?
        """;
        List<Product> products = jdbcTemplate.query(sql, (rs, rowNum) -> productOf(rs), userId);
        return products.stream().filter(Objects::nonNull).toList();
    }

    private Product productOf(ResultSet rs) {
        try {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            String imageUrl = rs.getString("image_url");
            if (id == 0 || name == null || price == 0 || imageUrl == null) {
                return null;
            }
            return new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url")
            );
        } catch (SQLException e) {
            return null;
        }
    }
}