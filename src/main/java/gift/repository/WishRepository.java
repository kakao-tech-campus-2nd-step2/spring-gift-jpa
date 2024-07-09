package gift.repository;

import gift.domain.Wish;
import gift.dto.WishRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Wish> wishRowMapper(){
        return (rs, rowNum) -> new Wish(rs.getLong("id"), rs.getLong("member_id"), rs.getLong("product_id"));
    }

    public List<Wish> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM wish WHERE member_id = ?";
        return jdbcTemplate.query(sql, wishRowMapper(), memberId);
    }

    public Wish save(WishRequest wishRequest, Long memberId) {
        String sql = "INSERT INTO wish (member_id, product_id) VALUES (?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, memberId);
            ps.setLong(2, wishRequest.getProductId());
            return ps;
        }, keyHolder);

        return new Wish(keyHolder.getKey().longValue(), memberId, wishRequest.getProductId());
    }

    public void delete(Long id, Long memberId) {
        String sql = "DELETE FROM wish WHERE id = ? AND member_id = ?";
        jdbcTemplate.update(sql, id, memberId);
    }
}

