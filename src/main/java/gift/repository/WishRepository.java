package gift.repository;

import gift.domain.Wish.createWish;
import gift.domain.Wish.wishDetail;
import gift.domain.Wish.wishSimple;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<wishSimple> getWishList(long id) {
        String sql = "SELECT * FROM Wish WHERE userId = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(wishSimple.class), id);
    }

    public wishDetail getWish(Long id) {
        String sql =
            "SELECT w.id as id, w.userId as userId, w.productId as productId, p.name as name, p.price as price, p.imageUrl as imageUrl "
                + "FROM Wish as w, product as p "
                + "WHERE w.id = ? and w.productId = p.id";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(wishDetail.class), id);
    }

    public int createWish(long userId, createWish create) {
        String sql = "INSERT INTO Wish (userId, productId) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        if (jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql,
                Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, userId);
            ps.setLong(2, create.getProductId());
            return ps;
        }, keyHolder) > 0) {
            return keyHolder.getKey().intValue();
        } else {
            return -1;
        }
    }

    public int deleteWish(Long id) {
        String sql = "DELETE FROM Wish WHERE id = ?";
        if (jdbcTemplate.update(sql, id) == 1) {
            return id.intValue();
        }
        return -1;

    }

    public boolean existWish(long userId, createWish create) {
        String sql = "SELECT EXISTS(SELECT 1 FROM wish WHERE userId = ? and productId = ?)";
        if (jdbcTemplate.queryForObject(sql, new Object[]{userId, create.getProductId()}, Integer.class) == 1) {

            return true;
        }
        return false;
    }

    public boolean validateId(Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM wish WHERE id = ?)";
        if (jdbcTemplate.queryForObject(sql, new Object[]{id}, Integer.class) == 1) {

            return true;
        }
        return false;
    }
}
