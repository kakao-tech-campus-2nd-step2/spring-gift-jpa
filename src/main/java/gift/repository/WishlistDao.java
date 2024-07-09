package gift.repository;

import gift.dto.Product;
import java.sql.Types;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistDao {

    private final JdbcClient jdbcClient;
    private final RowMapper<Product> productRowMapper = ((rs, rowNum) ->
        new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getLong("price"),
            rs.getString("imageUrl")
        ));

    public WishlistDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Integer insertProduct(String email, long productId) {
        String sql = """
            INSERT INTO Wishlist (email, productId)
            VALUES (:email, :productId);
            """;
        return jdbcClient.sql(sql)
            .param("email", email, Types.VARCHAR)
            .param("productId", productId, Types.BIGINT)
            .update();
    }

    public List<Product> findByEmail(String email) {
        String sql = """
            SELECT id, name, price, imageUrl
            FROM product, wishlist
            WHERE wishlist.email = :email AND wishlist.productId = product.id;
            """;
        return jdbcClient.sql(sql)
            .param("email", email)
            .query(productRowMapper)
            .list();
    }

    public Optional<Product> findByEmailAndProductId(String email, Long productId) {
        String sql = """
            SELECT id, name, price, imageUrl
            FROM product, wishlist
            WHERE wishlist.email = :email AND wishlist.productId = product.id AND product.id = :productId;
            """;
        return jdbcClient.sql(sql)
            .param("email", email)
            .param("productId", productId)
            .query(productRowMapper)
            .optional();
    }

    public Integer deleteProduct(String email, long productId) {
        String sql = "DELETE FROM wishlist WHERE email = :email AND productId = :productId;";
        return jdbcClient.sql(sql)
            .param("email", email)
            .param("productId", productId)
            .update();
    }
}
