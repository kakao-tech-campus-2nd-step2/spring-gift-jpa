package gift.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import gift.domain.WishList;

@Repository
public class WishListDao {
    
    private JdbcTemplate jdbcTemplate;

    public WishListDao(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WishList> findWishListById(long userId){
        var sql = "select product_id, user_id from wishlist where user_id = ?";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new WishList(
                        resultSet.getLong("product_id"),
                        resultSet.getLong("user_id"))
                ,
                userId);
    }

    public void insertWishList(long productId, long userId) {

        var sql = "INSERT INTO wishlist (product_id, user_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, productId,  userId);

    }

    public void deleteWishList(long productId, long userId) {

        var sql = "DELETE FROM wishlist WHERE product_id = ? AND user_id = ?";
        jdbcTemplate.update(sql, productId, userId);

    }

}
