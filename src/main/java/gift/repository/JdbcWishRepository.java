package gift.repository;

import gift.entity.Wish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcWishRepository implements WishRepository {
    private JdbcTemplate jdbcTemplate;
    private JdbcProductRepository productRepository;

    @Autowired
    public JdbcWishRepository(JdbcTemplate jdbcTemplate, JdbcProductRepository productRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.productRepository = productRepository;
    }

    public void addToWishlist(String email, String type, long productId) {
        String sql = "INSERT INTO wishlists (email, type, productId) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, email, type, productId);
    }

    public void removeFromWishlist(String email, String type, long productId) {
        String sql = "DELETE FROM wishlists WHERE email = ? AND type = ? AND productId = ?";
        jdbcTemplate.update(sql, email, type, productId);
    }


    public List<Wish> getWishlistItems(String email) {
        String sql = "SELECT * FROM wishlists WHERE email = ?";

        List<Wish> wishList = jdbcTemplate.query(
                sql,
                new Object[]{email},
                (resultSet, rowNum) -> {
                    Wish wish = new Wish(
                            resultSet.getString("email"),
                            resultSet.getString("type"),
                            resultSet.getLong("productId")
                    );
                    return wish;
                }
        );

        return wishList;
    }

}
