package gift.repository;

import gift.model.Wish;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepositoryImpl implements WishRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wish> wishRowMapper = (rs, rowNum) -> {
        Wish wish = new Wish();
        wish.setId(rs.getLong("id"));
        wish.setMemberId(rs.getLong("member_id"));
        wish.setProductName(rs.getString("product_name"));
        return wish;
    };

    @Override
    public List<Wish> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM wishes WHERE member_id = ?";
        return jdbcTemplate.query(sql, wishRowMapper, memberId);
    }

    @Override
    public Wish save(Wish wish) {
        String sql = "INSERT INTO wishes (member_id, product_name) VALUES (?, ?)";
        jdbcTemplate.update(sql, wish.getMemberId(), wish.getProductName());
        return wish;
    }

    @Override
    public boolean deleteByIdAndMemberId(Long id, Long memberId) {
        String sql = "DELETE FROM wishes WHERE id = ? AND member_id = ?";
        int rowsAffected = jdbcTemplate.update(sql, id, memberId);
        return rowsAffected > 0;
    }
}