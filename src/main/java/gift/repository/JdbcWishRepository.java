package gift.repository;

import gift.domain.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.HashMap;
import java.util.Map;

public class JdbcWishRepository implements WishRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcWishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("wish_list")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Wish save(Wish wish) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", wish.getUserId());
        parameters.put("product_id", wish.getProductId());
        parameters.put("created_at", wish.getCreatedAt());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        wish.setId(newId.longValue());

        return wish;
    }
}
