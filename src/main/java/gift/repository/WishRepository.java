package gift.repository;

import gift.model.Wish;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishRepository {
    private final JdbcTemplate jdbcTemplate;

    public WishRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Wish> wishRowMapper = (rs, rowNum) -> new Wish(
        rs.getLong("id"),
        rs.getLong("member_id"),
        rs.getLong("product_id")
    );

    public Optional<Wish> findById(Long id) {
        List<Wish> results = jdbcTemplate.query("SELECT * FROM wishlist WHERE id = ?", wishRowMapper, id);
        return results.isEmpty() ? Optional.empty() : Optional.of(results.getFirst());
    }

    public List<Wish> findAllByMemberId(Long memberId) {
        return jdbcTemplate.query("SELECT * FROM wishlist WHERE member_id = ?", wishRowMapper, memberId);
    }

    public Wish create(Wish wish) {
        jdbcTemplate.update("INSERT INTO wishlist (member_id, product_id) VALUES (?, ?)",
            wish.getMemberId(), wish.getProductId());
        return wish;
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM wishlist WHERE id = ?", id);
    }

    public boolean existsByMemberIdAndProductId(Long memberId, Long productId) {
        Integer count = jdbcTemplate.queryForObject(
            "SELECT COUNT(*) FROM wishlist WHERE member_id = ? AND product_id = ?",
            Integer.class, memberId, productId);
        return count != null && count > 0;
    }
}
