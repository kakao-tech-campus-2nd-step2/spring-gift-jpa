package gift.wishList;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("WishLists")
                .usingGeneratedKeyColumns("id");
    }

    public WishList insertWishList(long userId, WishListDTO wishList){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("userID", userId);
        parameters.put("productID", wishList.getProductID());
        parameters.put("count", wishList.getCount());
        long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new WishList(id, userId, wishList.getProductID(), wishList.count);
    }
    public WishList selectWishList(long id) {
        var sql = "select id, userID, productID, count from WishLists where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                getWishListRowMapper(),
                id
        );
    }

    public List<WishList> findWishListsByUserID(long userId) {
        var sql = "select id, userID, productID, count from WishLists where userID = ?";
        return jdbcTemplate.query(
                sql,
                getWishListRowMapper(),
                userId
        );
    }

    public List<WishList> selectWishLists(){
        var sql = "select id, userID, productID, count from WishLists";
        return jdbcTemplate.query(
                sql,
                getWishListRowMapper()
        );
    }

    private static RowMapper<WishList> getWishListRowMapper() {
        return (resultSet, rowNum) -> new WishList(
                resultSet.getLong("id"),
                resultSet.getLong("userID"),
                resultSet.getLong("productID"),
                resultSet.getLong("count")
        );
    }

    public void updateWishList(long id, long count){
        var sql = "update WishLists set count=? where id = ?";
        jdbcTemplate.update(sql,
                count,
                id);
    }

    public void deleteWishList(long id){
        var sql = "delete from WishLists where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
