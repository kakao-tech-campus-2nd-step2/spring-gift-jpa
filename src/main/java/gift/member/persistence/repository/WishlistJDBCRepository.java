package gift.member.persistence.repository;

import gift.global.exception.ErrorCode;
import gift.global.exception.NotFoundException;
import gift.member.persistence.entity.Wishlist;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistJDBCRepository implements WishlistRepository{
    private final JdbcTemplate jdbcTemplate;

    public WishlistJDBCRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Wishlist> getWishListByMemberId(Long memberId) {
        var sql = "SELECT * FROM wishlist WHERE member_id = ?";

        try {
            return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Wishlist(
                    rs.getLong("id"),
                    rs.getLong("product_id"),
                    rs.getLong("member_id"),
                    rs.getInt("count")
                ),
                memberId
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Wishlist with member id " + memberId + " not found");
        }

    }

    @Override
    public Long saveWishList(Wishlist wishList) {
        var sql = "INSERT INTO wishlist (product_id, member_id, count) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
            con -> {
                var ps = con.prepareStatement(sql, new String[]{"id"});
                ps.setLong(1, wishList.getProductId());
                ps.setLong(2, wishList.getMemberId());
                ps.setInt(3, wishList.getCount());
                return ps;
            },
            keyHolder
        );

        if(keyHolder.getKey() == null) {
            throw new RuntimeException("멤버 생성에 실패했습니다.");
        }
        return keyHolder.getKey().longValue();
    }

    @Override
    public Wishlist getWishListByMemberIdAndProductId(Long memberId, Long productId) {
        var sql = "SELECT * FROM wishlist WHERE member_id = ? AND product_id = ?";

        try {
            return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new Wishlist(
                    rs.getLong("id"),
                    rs.getLong("product_id"),
                    rs.getLong("member_id"),
                    rs.getInt("count")
                ),
                memberId, productId
            );
        } catch (EmptyResultDataAccessException e) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Wishlist with member id " + memberId + " and product id " + productId + " not found");
        }
    }

    @Override
    public Long updateWishlist(Wishlist wishList) {
        var sql = "UPDATE wishlist SET count = ? WHERE id = ?";

        int affectedRows = jdbcTemplate.update(sql,
            wishList.getCount(),
            wishList.getId());

        if (affectedRows == 0) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Wishlist with id " + wishList.getId() + " not found");
        }

        return wishList.getId();
    }

    @Override
    public void deleteWishlist(Long memberId, Long productId) {
        var sql = "DELETE FROM wishlist WHERE member_id = ? AND product_id = ?";

        int affectedRows = jdbcTemplate.update(sql, memberId, productId);

        if (affectedRows == 0) {
            throw new NotFoundException(ErrorCode.DB_NOT_FOUND,
                "Wishlist with member id " + memberId + " and product id " + productId + " not found");
        }
    }

    @Override
    public void deleteAll() {
        var sql = "DELETE FROM wishlist";
        jdbcTemplate.update(sql);
    }
}
