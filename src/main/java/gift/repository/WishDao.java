package gift.repository;

import gift.domain.Wish;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class WishDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public WishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("wish")
            .usingGeneratedKeyColumns("id");
    }

    public List<Wish> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM wish WHERE member_id = ?";
        return jdbcTemplate.query(
            sql,
            (resultSet, rowNum) -> new Wish(
                resultSet.getLong("id"),
                resultSet.getLong("member_id"),
                resultSet.getLong("product_id")
            ),
            memberId);
    }

    public void addWish(Wish wish) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("member_id", wish.getMemberId());
        parameters.put("product_id", wish.getProductId());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
    }

    public void deleteWishById(Long id) {
        var sql = "DELETE FROM wish WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

}