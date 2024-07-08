package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Wish;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public WishRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("Wish")
            .usingGeneratedKeyColumns("id");
    }

    public Wish save(Wish wish) {
        Map<String, Object> params = new HashMap<>();
        params.put("member_id", wish.getMemberId());
        params.put("product_id", wish.getProductId());

        Long wishId = (Long) simpleJdbcInsert.executeAndReturnKey(
            new MapSqlParameterSource(params));
        return new Wish(wishId, wish.getMemberId(), wish.getProductId());
    }

    public List<Wish> findAll(LoginMember loginMember) {
        var sql = "SELECT id, member_id, product_id FROM Wish WHERE member_id = ?";

        return jdbcTemplate.query(sql, getWishRowMapper(), loginMember.id());
    }

    public Wish findById(Long id, LoginMember loginMember) throws DataAccessException {
        var sql = "SELECT id, member_id, product_id FROM Wish WHERE member_id = ? AND id = ?";

        return jdbcTemplate.queryForObject(sql, getWishRowMapper(), loginMember.id(), id);
    }

    public void delete(Long id) {
        var sql = "DELETE FROM Wish WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Wish> getWishRowMapper() {
        return (resultSet, rowNum) -> new Wish(
            resultSet.getLong("id"),
            resultSet.getLong("member_id"),
            resultSet.getLong("product_id")
        );
    }
}
