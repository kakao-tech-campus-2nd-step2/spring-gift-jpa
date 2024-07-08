package gift.wishlist.dao;

import gift.product.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WishesDao {

    private final JdbcTemplate jdbcTemplate;

    public WishesDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void save(Long memberId, Long productId) {
        String sql = "INSERT INTO wishes (member_id, product_id) VALUES(?, ?)";
        jdbcTemplate.update(sql, memberId, productId);
    }

    public void remove(Long memberId, Long productId) {
        String sql = "DELETE FROM wishes WHERE member_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, memberId, productId);
    }

    public boolean exists(Long memberId, Long productId) {
        String sql = "SELECT EXISTS(SELECT * FROM wishes WHERE member_id = ? AND product_id = ?)";
        Boolean result = jdbcTemplate.queryForObject(sql,
                Boolean.class,
                memberId,
                productId);

        return result != null && result;
    }

    public List<Product> findWishlistOfMember(Long memberId) {
        String sql = """
                SELECT p.id, p.name, p.price, p.image_url
                FROM product AS p
                INNER JOIN wishes AS w
                ON p.id = w.product_id
                WHERE w.member_id = ?
                """;

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                ),
                memberId
        );
    }

}
