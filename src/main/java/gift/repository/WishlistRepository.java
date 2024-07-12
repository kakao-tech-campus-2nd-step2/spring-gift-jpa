package gift.repository;

import gift.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WishlistRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createWishlistTable();
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("wishlist_items")
            .usingGeneratedKeyColumns("id");
    }

    public void createWishlistTable() {
        String sql = "CREATE TABLE IF NOT EXISTS wishlist_items (" +
            "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
            "member_id BIGINT NOT NULL," +
            "product_id BIGINT NOT NULL," +
            "product_name VARCHAR(255) NOT NULL," +
            "product_price BIGINT NOT NULL" +
            ")";
        jdbcTemplate.execute(sql);
    }

    public WishList addProduct(WishList wishlist) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", wishlist.getMemberId());
        parameters.put("product_id", wishlist.getProductId());
        parameters.put("product_name", wishlist.getName());
        parameters.put("product_price", wishlist.getPrice());
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        wishlist.setId(newId.longValue());
        return wishlist;
    }

    public List<WishList> getProductsByMemberId(Long memberId) {
        String sql = "SELECT * FROM wishlist_items WHERE member_id = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            Long id = rs.getLong("id");
            Long productId = rs.getLong("product_id");
            String name = rs.getString("product_name");
            int price = rs.getInt("product_price");
            return new WishList(id, memberId, productId, name, price);
        }, memberId);
    }

    public void deleteItem(Long id) {
        String sql = "DELETE FROM wishlist_items WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
