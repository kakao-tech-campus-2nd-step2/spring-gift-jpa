package gift.wishlist.infrastructure;

import gift.exception.type.DataAccessException;
import gift.wishlist.domain.Wishlist;
import gift.wishlist.domain.WishlistRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class WishlistJDBCRepository implements WishlistRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Wishlist> wishlistRowMapper;

    public WishlistJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("wishlist")
                .usingGeneratedKeyColumns("id");
        this.wishlistRowMapper = wishlistRowMapper();
    }

    @Override
    public List<Wishlist> findByMemberEmail(String memberEmail) {
        try {
            String sql = "SELECT * FROM wishlist WHERE member_email = ?";
            return jdbcTemplate.query(sql, wishlistRowMapper, memberEmail);
        } catch (Exception e) {
            throw new DataAccessException("사용자의 위시리스트를 조회하는 중에 문제가 발생했습니다.");
        }
    }

    @Override
    public Optional<Wishlist> findById(Long wishlistId) {
        try {
            String sql = "SELECT * FROM wishlist WHERE id = ?";
            return jdbcTemplate.query(sql, wishlistRowMapper, wishlistId)
                    .stream()
                    .findFirst();
        } catch (Exception e) {
            throw new DataAccessException("특정 위시리스트 항목을 조회하는 중에 문제가 발생했습니다.");
        }
    }

    @Override
    public void addWishlist(Wishlist wishlist) {
        try {
            simpleJdbcInsert.execute(
                    Map.of(
                            "member_email", wishlist.getMemberEmail(),
                            "product_id", wishlist.getProductId()
                    )
            );
        } catch (Exception e) {
            throw new DataAccessException("위시리스트 항목을 추가하는 중에 문제가 발생했습니다.");
        }
    }

    @Override
    public void deleteWishlist(Long wishlistId) {
        try {
            String sql = "DELETE FROM wishlist WHERE id = ?";
            jdbcTemplate.update(sql, wishlistId);
        } catch (Exception e) {
            throw new DataAccessException("위시리스트 항목을 삭제하는 중에 문제가 발생했습니다.");
        }
    }

    private RowMapper<Wishlist> wishlistRowMapper() {
        return (rs, rowNum) -> new Wishlist(
                rs.getLong("id"),
                rs.getString("member_email"),
                rs.getLong("product_id")
        );
    }
}
