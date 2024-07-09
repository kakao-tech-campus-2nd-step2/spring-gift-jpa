package gift.repository;

import gift.domain.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepositoryImpl implements WishRepository{
    private final JdbcTemplate jdbcTemplate;

    public WishRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public void addWish(Wish wish) {
        var sql = "INSERT INTO wishes (user_id,product_id,quantity) VALUES(?,?,?)";
        Object[] params = new Object[]{wish.getUserId(), wish.getProductId(), wish.getQuantity()};
        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<List<Wish>> findById(Long id) {
        var sql = "SELECT * FROM wishes WHERE user_id = ?";
        List<Wish> wishes = jdbcTemplate.query(
            sql,
            (result, rowNum) -> new Wish(
                result.getLong("ID"),
                result.getLong("USER_ID"),
                result.getLong("PRODUCT_ID"),
                result.getInt("QUANTITY")
            ),
            id
        );
        if (wishes.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(wishes);
    }

    @Override
    public void deleteWish(Long userId, Long productId) {
        var sql = "DELETE wishes WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql,userId,productId);
    }

    @Override
    public void updateWish(Long userId, Long productId, int quantity) {
        var sql = "UPDATE wishes SET quantity = ? WHERE user_id = ? AND product_id = ?";
        jdbcTemplate.update(sql,quantity,userId,productId);
    }
}
