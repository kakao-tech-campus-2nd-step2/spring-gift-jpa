package gift.model.dao;

import gift.model.Wish;
import gift.model.repository.WishRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class WishDao implements WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Wish entity) {
        if (entity.isNew()) {
            jdbcTemplate.update(WishQuery.INSERT_WISH, entity.getUserId(), entity.getProductId(), entity.getAmount(),
                    entity.isDeleted());
            return;
        }
        update(entity);
    }

    @Override
    public void update(Wish entity) {
        jdbcTemplate.update(WishQuery.UPDATE_WISH, entity.getAmount(), entity.getId(), entity.isDeleted());
    }

    @Override
    public Optional<Wish> findByIdAndUserId(Long id, Long userId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(WishQuery.SELECT_WISH_BY_ID_AND_USER_ID,
                            new Object[]{id, userId}, (rs, rowNum) -> wishMapper(rs)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Wish> findWishesByUserId(Long userId) {
        return jdbcTemplate.query(WishQuery.SELECT_WISH_BY_USER_ID, new Object[]{userId},
                (rs, rowNum) -> wishMapper(rs));
    }

    public Wish wishMapper(ResultSet rs) throws SQLException {
        return new Wish(
                rs.getLong("id"),
                rs.getLong("user_id"),
                rs.getLong("product_id"),
                rs.getInt("amount")
        );
    }
}
