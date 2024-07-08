package gift.wishlist.repository;

import gift.wishlist.domain.WishList;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WishList> findByMemberId(Long memberId) {
        return jdbcTemplate.query("SELECT * FROM wish_lists WHERE member_id = ?",
            BeanPropertyRowMapper.newInstance(WishList.class), memberId);
    }

    public WishList.JoinedWishList getWishListDetail(Long wishId) {
        String sql =
            "SELECT w.id as id, w.memberId as memberId, w.productId as productId, p.name as name, p.price as price, p.imageUrl as imageUrl "
                + "FROM wish_lists as w, products as p "
                + "WHERE w.id = ? and w.productId = p.id";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(WishList.JoinedWishList.class), wishId);
    }

    public void addWishListItem(WishList item) {
        jdbcTemplate.update("INSERT INTO wish_lists (member_id, product_id) VALUES (?, ?)",
            item.getMemberId(), item.getProductId());
    }

    public void deleteWishListItem(Long id) {
        jdbcTemplate.update("DELETE FROM wish_lists WHERE id = ?", id);
    }

}
