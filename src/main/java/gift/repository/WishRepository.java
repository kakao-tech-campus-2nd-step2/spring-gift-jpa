package gift.repository;

import gift.domain.Wish;
import gift.controller.wish.WishDto;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Wish> WISH_ROW_MAPPER = (resultSet, rowNum) -> new Wish(
        resultSet.getLong("productId"),
        resultSet.getLong("count")
    );

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> findAll(String email) {
        String sql = "SELECT * FROM wishes WHERE email = ?";
        return jdbcTemplate.query(sql, new Object[]{email}, WISH_ROW_MAPPER);
    }

    public Optional<Wish> find(String email, Long productId) {
        try {
            String sql = "SELECT * FROM wishes WHERE email = ? AND productId = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, WISH_ROW_MAPPER,
                email,
                productId
            ));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }

    public Wish save(String email, WishDto wish) {
        String sql = "INSERT INTO wishes (email, productId, count) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, email, wish.productId(), wish.count());
        return find(email, wish.productId()).get();
    }

    public Wish update(String email, WishDto wish) {
        String sql = "UPDATE wishes SET count = ? WHERE email = ? AND productId = ?";
        jdbcTemplate.update(sql, wish.count(), email, wish.productId());
        return find(email, wish.productId()).get();
    }

    public void delete(String email, Long productId) {
        String sql = "DELETE FROM wishes WHERE email = ? AND productId = ?";
        jdbcTemplate.update(sql, email, productId);
    }
}
