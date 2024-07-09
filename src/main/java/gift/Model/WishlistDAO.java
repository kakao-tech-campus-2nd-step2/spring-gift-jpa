package gift.Model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class WishlistDAO {
    private final JdbcTemplate jdbcTemplate;

    public WishlistDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertWishlist(Wishlist wishlist, String email){
        var sql = "insert into wishlist (email, name, price, quantity) values (?,?,?,?)";
        jdbcTemplate.update(sql, email, wishlist.name(), wishlist.price(), wishlist.quantity());
    }

    public void deleteWishlist(String email, String name){
        var sql = "delete from wishlist where email=? and name=?";
        jdbcTemplate.update(sql, email, name);
    }

    public List<Wishlist> selectAllWishlist(String email){
        var sql = "select name, price, quantity from wishlist where email=?";
        List<Wishlist> wishlists = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Wishlist wishlist = new Wishlist(
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getInt("quantity")
                    );
                    return wishlist;
                }, email);
        return wishlists;
    }
}
