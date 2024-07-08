package gift.repository;

import java.util.List;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import gift.exception.CustomException.ItemNotFoundException;
import gift.exception.ErrorCode;
import gift.model.item.Item;
import gift.model.wishList.WishItem;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insert(WishItem wishItem) {
        try {
            var sql = "INSERT INTO wish (userid, itemid) VALUES (?, ?)";
            jdbcTemplate.update(sql, wishItem.getUserId(), wishItem.getItemId());
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyException("이미 존재하는 상품입니다.");
        }
    }

    public WishItem findByUserIdAndItemId(String userId, Long itemId) {
        try {
            WishItem wishItem = jdbcTemplate.queryForObject(
                "SELECT * FROM wish WHERE userid = ? AND itemid = ?",
                new Object[]{userId, itemId},
                new BeanPropertyRowMapper<>(WishItem.class)
            );
            return wishItem;
        } catch (Exception e) {
            throw new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND);
        }
    }


    public List<Item> findAllByUserId(Long userId) {
        return jdbcTemplate.query(
            "SELECT * FROM item WHERE id in (select itemId from wish where userId = ?)",
            new Object[]{userId},
            (rs, rowNum) -> new Item(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("price"),
                rs.getString("imgUrl")
            )
        );
    }


    public void delete(Long userId, Long productId) {
        jdbcTemplate.update(
            "DELETE FROM wish WHERE userid = ? AND itemid = ?",
            userId, productId
        );
    }
}