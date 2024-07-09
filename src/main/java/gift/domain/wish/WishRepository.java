package gift.domain.wish;

import gift.web.exception.ProductNotFoundException;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {
    private JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private RowMapper<Wish> wishRowMapper() {
        return (rs, rowNum) -> new Wish(
            rs.getLong("id"),
            rs.getString("email"),
            rs.getLong("productId"),
            rs.getLong("count")
        );
    }
    public List<Wish> selectAllWishes(String email) {
        var sql = "select * from wishes where email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, wishRowMapper());
    }

    public Wish insertWish(Wish wish) {
        var sql = "insert into wishes(email, productId, count) values(?, ?, ?)";
        jdbcTemplate.update(sql, wish.email(), wish.productId(), wish.count());
        return getWish(wish.email(), wish.productId());
    }

    public Wish getWish(String email, long productId) {
        var sql = "select * from wishes where email = ? and productId = ?";
        try {
            return jdbcTemplate.queryForObject(sql, wishRowMapper(), email, productId);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("위시 상품이 존재하지 않습니다.");
        }
    }

    public void updateWish(Wish wish) {
        if (wish.count() == 0) {
            deleteWish(wish.email(), wish.productId());
            return;
        }
        var sql = "update wishes set count = ? where email = ? and productId = ?";
        jdbcTemplate.update(sql, wish.count(), wish.email(), wish.productId());
    }

    public void deleteWish(String email, long productId) {
        var sql = "delete from wishes where email = ? and productId = ?";
        jdbcTemplate.update(sql, email, productId);
    }
}
