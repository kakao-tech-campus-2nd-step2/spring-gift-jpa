package gift.wishlist.repository;

import gift.wishlist.domain.WishList;
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

    private RowMapper<WishList> wishRowMapper = (rs, rowNum) -> {
        WishList item = new WishList();
        item.setId(rs.getLong("id"));
        item.setMemberId(rs.getLong("member_id"));
        item.setProductName(rs.getString("product_name"));
        item.setProductPrice(rs.getLong("product_price"));
        return item;
    };

    public List<WishList> findByMemberId(Long memberId) {
        return jdbcTemplate.query("SELECT * FROM wish_lists WHERE member_id = ?", wishRowMapper, memberId);
    }

    public void addWishListItem(WishList item) {
        jdbcTemplate.update("INSERT INTO wish_lists (member_id, product_name, product_price) VALUES (?, ?, ?)",
            item.getMemberId(), item.getProductName(), item.getProductPrice());
    }

    public void deleteWishListItem(Long id) {
        jdbcTemplate.update("DELETE FROM wish_lists WHERE id = ?", id);
    }

}
