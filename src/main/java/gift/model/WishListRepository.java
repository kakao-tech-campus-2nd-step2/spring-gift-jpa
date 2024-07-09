package gift.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<WishList> wishListRowMapper = (ResultSet, rowNum) ->
        new WishList(
                ResultSet.getLong("id"),
                ResultSet.getLong("memberId"),
                ResultSet.getLong("productId")
        );

    public List<WishList> findByMemberId(Long memberId) {
        return jdbcTemplate.query("SELECT * FROM wish_lists WHERE memberId = ?", wishListRowMapper, memberId);
    }

    public void addProductToWishList(Long memberId, Long productId) {
        jdbcTemplate.update("INSERT INTO wish_lists (memberId, productId) VALUES (?, ?)", memberId, productId);
    }

    public void removeProductFromWishList(Long memberId, Long productId) {
        jdbcTemplate.update("DELETE FROM wish_lists WHERE memberId = ? AND productId = ?", memberId, productId);
    }
}
