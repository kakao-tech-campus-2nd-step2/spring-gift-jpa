package gift.repository;

import gift.domain.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.Map;

public class JdbcWishListRepository implements WishListRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcWishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("wish_list")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public WishList save(WishList wishList) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", wishList.getUserId());
        parameters.put("product_id", wishList.getProductId());
        parameters.put("created_at", wishList.getCreatedAt());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        wishList.setId(newId.longValue());

        return wishList;
    }
}
