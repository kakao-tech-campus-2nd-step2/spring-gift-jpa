package gift.product.infra;

import gift.product.domain.WishList;
import gift.product.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class WishListRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<WishList> wishListRowMapper = new RowMapper<WishList>() {
        @Override
        public WishList mapRow(ResultSet rs, int rowNum) throws SQLException {
            WishList wishList = new WishList();
            wishList.setId(rs.getLong("id"));
            wishList.setUserId(rs.getLong("user_id"));
            return wishList;
        }
    };

    public WishList save(WishList wishList) {
        String sql = "INSERT INTO WishList (user_id) VALUES (?)";
        jdbcTemplate.update(sql, wishList.getUserId());
        return wishList;
    }

    public WishList findByUserId(Long userId) {
        String sql = "SELECT * FROM WishList WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, wishListRowMapper, userId);
    }

    public void addProductToWishList(Long wishListId, Product product) {
        String sql = "INSERT INTO WishList_Product (wishlist_id, product_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, wishListId, product.getId());
    }

    public List<Product> findProductsByWishListId(Long wishListId) {
        String sql = "SELECT p.* FROM Product p INNER JOIN WishList_Product wp ON p.id = wp.product_id WHERE wp.wishlist_id = ?";
        return jdbcTemplate.query(sql, new RowMapper<Product>() {
            @Override
            public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
                Product product = new Product();
                product.setId(rs.getLong("id"));
                product.setName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("imageUrl"));
                return product;
            }
        }, wishListId);
    }


    public void deleteProductFromWishList(Long wishListId, Long productId) {
        String sql = "DELETE FROM WishList_Product WHERE wishlist_id = ? AND product_id = ?";
        jdbcTemplate.update(sql, wishListId, productId);
    }
}
