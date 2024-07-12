package gift.repository;

import gift.model.Wish;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public WishRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    private final RowMapper<Wish> wishRowMapper = (rs, rowNum) -> {
        Wish wish = new Wish();
        wish.setId(rs.getLong("id"));
        wish.setMemberId(rs.getLong("member_id"));
        wish.setProductId(rs.getLong("product_id"));
        return wish;
    };

    public List<Wish> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM wishes WHERE member_id = :memberId";
        MapSqlParameterSource params = new MapSqlParameterSource("memberId", memberId);
        return namedParameterJdbcTemplate.query(sql, params, wishRowMapper);
    }

    public Wish save(Wish wish) {
        String sql = "INSERT INTO wishes (member_id, product_id) VALUES (:memberId, :productId)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", wish.getMemberId())
                .addValue("productId", wish.getProductId());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbcTemplate.update(sql, params, keyHolder, new String[]{"id"});
        wish.setId(keyHolder.getKey().longValue());
        return wish;
    }

    public void deleteByMemberIdAndId(Long memberId, Long id) {
        String sql = "DELETE FROM wishes WHERE member_id = :memberId AND id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("memberId", memberId)
                .addValue("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
