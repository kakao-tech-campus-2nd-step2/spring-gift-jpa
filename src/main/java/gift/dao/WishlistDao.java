package gift.dao;

import gift.model.Product;
import gift.model.Wishlist;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class WishlistDao {
    private final JdbcTemplate jdbcTemplate;

    public WishlistDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public RowMapper<Wishlist> WishlistRowMapper() {
        return (resultSet, rowNum) -> new Wishlist(
                resultSet.getLong("id"),
                resultSet.getLong("userid"),
                resultSet.getLong("productid")
        );
    }

    public List<Wishlist> selectAllWishlist(Long id){
        var sql = "select * from wishlist where userid = ?";
        List<Wishlist> list = new ArrayList<>();
        return jdbcTemplate.query(sql, WishlistRowMapper(), id);
    }

    public void insertWishlist(Wishlist wishlist){
        var sql = "insert into wishlist (userid, productid) values (?, ?)";
        jdbcTemplate.update(sql, wishlist.getUserId(), wishlist.getProductId());
    }

    public void deleteWishlist(Long id){
        var sql = "delete from wishlist where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
