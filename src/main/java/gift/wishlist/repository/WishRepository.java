package gift.wishlist.repository;

import gift.wishlist.model.Wish;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public WishRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("wishes")
            .usingGeneratedKeyColumns("id");
    }

    public List<Wish> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM wishes WHERE member_id = ?";
        return jdbcTemplate.query(sql, new Object[]{memberId}, (resultSet, rowNum) -> {
            Wish wish = new Wish();
            wish.setId(resultSet.getLong("id"));
            wish.setMemberId(resultSet.getLong("member_id"));
            wish.setProductName(resultSet.getString("product_name"));
            return wish;
        });
    }

    public Optional<Wish> findByName(Long memberId, String productName) {
        String sql = "SELECT * FROM wishes WHERE member_id = ? AND product_name = ?";
        List<Wish> wishes = jdbcTemplate.query(sql, new Object[]{memberId, productName}, (resultSet, rowNum) -> {
            Wish wish = new Wish();
            wish.setId(resultSet.getLong("id"));
            wish.setMemberId(resultSet.getLong("member_id"));
            wish.setProductName(resultSet.getString("product_name"));
            return wish;
        });
        if (wishes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(wishes.get(0));
    }


    public void save(Wish wish) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", wish.getMemberId());
        params.put("product_name", wish.getProductName());

        Number newId = simpleJdbcInsert.executeAndReturnKey(params);
        wish.setId(newId.longValue());
    }

    public void deleteByProductName(Long memberId, String productName) {
        String sql = "DELETE FROM wishes WHERE member_id = ? AND product_name = ?";
        jdbcTemplate.update(sql, memberId, productName);
    }

}
