package gift.repository;

import gift.dto.Wishlist;
import gift.dto.Wishlist.Request;
import gift.dto.Wishlist.Response;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//@Repository
@AllArgsConstructor
public class WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<Wishlist.Response> findAllWishlistItems(long userId) {
        String sql =
            """
                SELECT
                    p.name AS product_name,
                    c.quantity
                FROM
                    user_tb u
                INNER JOIN
                    wishlist_tb c ON u.id = c.user_id
                INNER JOIN
                    products_tb p ON c.product_id = p.id
                WHERE
                    u.id = ?""";
        return jdbcTemplate.query(sql, itemRowMapper(), userId);
    }
    public void save(long userId, long productId, Request request) {
        String sql = "INSERT INTO wishlist_tb (user_id, product_id, quantity) VALUES(?,?,?)";
        jdbcTemplate.update(sql, userId, productId, request.getQuantity());
    }

    public void deleteByProductId(long userId, long productId) {
        String sql = "DELETE FROM wishlist_tb WHERE user_id=? AND product_id=?";
        jdbcTemplate.update(sql,userId, productId);
    }

    private static RowMapper<Wishlist.Response> itemRowMapper() {
        return new RowMapper<Wishlist.Response>() {
            @Override
            public Wishlist.Response mapRow(ResultSet rs, int rowNum) throws SQLException {
                return new Response(rs.getString("product_name"), rs.getInt("quantity"));
            }
        };
    }
}
