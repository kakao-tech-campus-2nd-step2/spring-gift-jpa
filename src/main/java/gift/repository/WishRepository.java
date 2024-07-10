package gift.repository;

import gift.entity.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;


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

    public Wish save(Long productId, Long userId) {

        Map<String, Object> parameters = Map.of("product_id", productId, "user_id", userId);

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        Long id = newId.longValue();

        return new Wish(id, productId, userId);
    }

    public List<Wish> getAll(Long userId) {
        var sql = """
                select * from wishes
                where id =? 
                """;
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum)
                        -> new Wish(
                        resultSet.getLong("id"),
                        resultSet.getLong("product_id"),
                        resultSet.getLong("user_id")
                ),
                userId
        ).stream().toList();
    }

    public void delete(Long id, Long userId) throws IllegalAccessException {

        Wish wish = findOneById(id);

        if (wish.getUserId().equals(userId)) {
            throw new IllegalAccessException();
        }

        var sql = "delete from wishes where id = ?";

        jdbcTemplate.update(sql, id);
    }

    public Wish findOneById(Long id) {
        var sql = "select id, productId, token from wishes where id= ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new Wish(
                        id,
                        resultSet.getLong("product_id"),
                        resultSet.getLong("user_id")),
                id
        );
    }
}
