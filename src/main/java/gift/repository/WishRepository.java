package gift.repository;

import gift.domain.Product;
import gift.domain.Wish;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Wish> wishRowMapper(){
        return (rs, rowNum) -> {
            Wish wish = new Wish();
            wish.setId(rs.getLong("id"));
            wish.setMemberId(rs.getLong("member_id"));
            wish.setProductId(rs.getLong("product_id"));
            return wish;
        };
    }

    public List<Wish> findByMemberId(Long memberId) {
        String sql = "SELECT * FROM Wish WHERE member_id = ?";
        return jdbcTemplate.query(sql, wishRowMapper(), memberId);
    }

    public void save(Wish wish) {
        String sql = "INSERT INTO Wish (member_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, wish.getMemberId(), wish.getProductId());
    }

    public void delete(Long id, Long memberId) {
        String sql = "DELETE FROM Wish WHERE id = ? AND member_id = ?";
        jdbcTemplate.update(sql, id, memberId);
    }
}

