package gift.repository;

import gift.domain.WishlistItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class WishlistJDBCRepository implements WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishlistJDBCRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public void addItem(WishlistItem item) {
        String sql = "INSERT INTO wishlist (member_id, item_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, item.getMemberId(), item.getItemName());
        Long id = jdbcTemplate.queryForObject("SELECT LAST_INSERT_ID()", Long.class);
        item.setId(id); // WishlistItem 객체에 자동 생성된 id 할당
    }
    @Override
    public void deleteItem(Long itemId) {
        String sql = "DELETE FROM wishlist WHERE id = ?";
        jdbcTemplate.update(sql, itemId);
    }
    @Override
    public List<WishlistItem> getItemsByMemberId(Long memberId) {
        String sql = "SELECT * FROM wishlist WHERE member_id = ?";
        return jdbcTemplate.query(sql, wishlistItemRowMapper(), memberId);
    }

    private RowMapper<WishlistItem> wishlistItemRowMapper() {
        return (rs, rowNum) -> new WishlistItem(
                rs.getLong("id"),
                rs.getLong("member_id"),
                rs.getString("item_name")
        );
    }
}
