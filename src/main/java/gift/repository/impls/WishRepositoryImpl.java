package gift.repository.impls;

import gift.domain.Wish;
import gift.repository.WishRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class WishRepositoryImpl implements WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Wish wish) {
        var sql = "INSERT INTO WISHES (USER_ID, PRODUCT_ID, QUANTITY) VALUES (?,?,?)";
        jdbcTemplate.update(sql, wish.getUserId(), wish.getProductId(), wish.getQuantity());
    }

    @Override
    public Optional<List<Wish>> findByUserId(Long userId) {
        var sql = "SELECT * FROM WISHES WHERE USER_ID = ?";
        List<Wish> wishes = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Wish(
                        resultSet.getLong("ID"),
                        resultSet.getLong("USER_ID"),
                        resultSet.getLong("PRODUCT_ID"),
                        resultSet.getInt("QUANTITY")
                ),
                userId
        );
        if (wishes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(wishes);
    }

    @Override
    public Optional<Wish> findByIdAndUserId(Long id, Long userId) {
        var sql = "SELECT * FROM WISHES WHERE ID = ? AND USER_ID = ?";
        List<Wish> wishes = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Wish(
                        resultSet.getLong("ID"),
                        resultSet.getLong("USER_ID"),
                        resultSet.getLong("PRODUCT_ID"),
                        resultSet.getInt("QUANTITY")
                ),
                id,
                userId
        );
        if (wishes.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(wishes.get(0));
    }

    @Override
    public void delete(Long id) {
        var sql = "DELETE FROM WISHES WHERE ID = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void updateQuantity(Long id, int quantity) {
        var sql = "UPDATE WISHES SET QUANTITY = ? WHERE ID = ?";
        jdbcTemplate.update(sql, quantity, id);
    }
}
