package gift.entity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class WishDao {
    private final JdbcTemplate jdbcTemplate;

    public WishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Wish insertWish(Wish wish) {
        var sql = "insert into wish (user_id, product_id) values (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, wish.userId);
            ps.setLong(2, wish.productId);
            return ps;
        }, keyHolder);
        Long wishId = keyHolder.getKey().longValue();
        return new Wish(wishId, wish.userId, wish.productId);
    }

    public Optional<Wish> selectWish(Long id) {
        var sql = "select id, user_id, product_id from wish where id = ?";
        List<Wish> wishes = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Wish(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("product_id")
                ),
                id
        );
        return wishes.stream().findFirst();
    }

    public List<Wish> selectWishesByUserId(Long userId) {
        var sql = "select id, user_id, product_id from wish where user_id = ?";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Wish(
                        resultSet.getLong("id"),
                        resultSet.getLong("user_id"),
                        resultSet.getLong("product_id")
                ),
                userId
        );
    }

    public void deleteWish(Long id) {
        var sql = "delete from wish where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
