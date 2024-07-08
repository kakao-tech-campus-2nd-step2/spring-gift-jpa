package gift.repository;

import gift.model.Wish;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishDao {

    private final JdbcTemplate jdbcTemplate;

    public WishDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Wish> selectAllWishesByMemberId(Long memberId) {
        return jdbcTemplate.query(
            WishQuery.SELECT_ALL_WISHES_BY_MEMBERID.getQuery(),
            (resultSet, rowNum) -> new Wish(
                resultSet.getLong("member_id"),
                resultSet.getString("product_name"),
                resultSet.getInt("count")
            ),
            memberId
        );
    }

    public void insertWish(Wish wish) {
        jdbcTemplate.update(WishQuery.INSERT_WISH_BY_MEMBERID.getQuery(), wish.getProductName(),
            wish.getCount(), wish.getMemberId());
    }

    public void updateWish(Wish wish) {
        jdbcTemplate.update(WishQuery.UPDATE_WISH_BY_MEMBERID_PRODUCTNAME.getQuery(), wish.getCount(),
            wish.getMemberId(), wish.getProductName());
    }

    public void deleteWish(Wish wish) {
        jdbcTemplate.update(WishQuery.DELETE_WISH_BY_MEMBERID_PRODUCTNAME.getQuery(),
            wish.getMemberId(), wish.getProductName());
    }
}
