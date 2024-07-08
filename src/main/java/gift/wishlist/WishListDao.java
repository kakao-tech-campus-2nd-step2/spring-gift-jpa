package gift.wishlist;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishListDao {

    private final JdbcTemplate jdbcTemplate;

    public WishListDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private WishList mapRowToProduct(ResultSet resultSet, int rowNum) throws SQLException {
        return new WishList(
            resultSet.getString("email"),
            resultSet.getString("name"),
            resultSet.getInt("num")
        );
    }

    public void insertWishList(WishList wishList) {
        String sql = "INSERT INTO wishlist (email, name, num) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, wishList.getEmail(), wishList.getName(), wishList.getNum());
    }

    public List<WishList> selectWishListsByEmail(String email){
        String sql = "SELECT * FROM wishlist WHERE email = ?";
        return jdbcTemplate.query(sql, this::mapRowToProduct,email);
    }

    public WishList selectWishListByEmailAndName(String email, String name){
        String sql = "SELECT email, name, num FROM wishlist WHERE email = ? AND name = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToProduct, email, name);
    }

    public void updateWishList(String email, String name, int num) {
        String sql = "UPDATE wishlist SET num = ? WHERE email = ? AND name = ?";
        jdbcTemplate.update(sql, num, email, name);
    }

    public boolean deleteWishList(String email, String name) {
        String sql = "DELETE FROM wishlist WHERE email = ? AND name = ?";
        jdbcTemplate.update(sql, email, name);
        return true;
    }

    public List<WishList> findAll() {
        String sql = "SELECT * FROM wishlist";
        return jdbcTemplate.query(sql, this::mapRowToProduct);
    }
}
