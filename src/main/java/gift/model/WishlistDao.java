package gift.model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
        return ( (resultSet, rowNum) -> {
            Wishlist wishlist = new Wishlist();
            wishlist.setId(resultSet.getLong("id"));
            wishlist.setUserId(resultSet.getLong("userid"));
            wishlist.setProductId(resultSet.getLong("productid"));
            return wishlist;
        });
    }

    public List<Wishlist> selectAllWishlist(Long id){
        var sql = "select * from wishlist where userid = ?";
        List<Wishlist> list = new ArrayList<>();
        System.out.println(jdbcTemplate.query(sql, WishlistRowMapper(), id));
        return jdbcTemplate.query(sql, WishlistRowMapper(), id);
    }

    public void insertWishlist(Wishlist wishlist){
        var sql = "insert into wishlist (id, userid, productid) values (?, ?, ?)";
        jdbcTemplate.update(sql, wishlist.getId(), wishlist.getUserId(), wishlist.getProductId());


    }

    public void deleteWishlist(Long id){
        var sql = "delete from wishlist where id = ?";
        jdbcTemplate.update(sql, id);

    }
}
