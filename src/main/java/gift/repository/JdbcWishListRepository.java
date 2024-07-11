package gift.repository;

import gift.model.WishListItem;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcWishListRepository implements WishListRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcWishListRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("wishlist")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<WishListItem> wishListItemRowMapper = (rs, rowNum) -> new WishListItem(
            rs.getLong("id"),
            rs.getLong("member_id"),
            rs.getLong("product_id")
    );

    @Override
    public void addWishListItem(WishListItem item) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", item.getMemberId());
        parameters.put("product_id", item.getProductId());

        simpleJdbcInsert.execute(parameters);
    }

    @Override
    public void removeWishListItem(Long id) {
        jdbcTemplate.update("DELETE FROM wishlist WHERE id = ?", id);
    }

    @Override
    public List<WishListItem> findWishListByMemberId(Long memberId) {
        return jdbcTemplate.query(
                "SELECT * FROM wishlist WHERE member_id = ?",
                wishListItemRowMapper,
                memberId
        );
    }

    @Override
    public Optional<WishListItem> findById(Long id) {
        List<WishListItem> items = jdbcTemplate.query(
                "SELECT * FROM wishlist WHERE id = ?",
                wishListItemRowMapper,
                id
        );
        return items.stream().findFirst();
    }
}
