package gift.repository;

import gift.model.WishList;
import gift.model.WishListDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcWishlistRepository implements WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcWishlistRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public boolean addWishlist(String email, WishListDTO wishListDTO) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("wishlist");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("email", email);
        parameters.put("productId", wishListDTO.getProductId());
        parameters.put("count", wishListDTO.getCount());

        int execute = jdbcInsert.execute(parameters);

        return execute != 0;
    }

    @Override
    public boolean removeWishlist(String email, Long productId) {
        int update = jdbcTemplate.update("delete from wishlist where email = ? and productId = ?", email, productId);
        return update != 0;
    }

    @Override
    public boolean updateWishlist(String email, WishListDTO wishListDTO) {
        int update = jdbcTemplate.update("update wishlist set email = ?, productId = ?, count = ? where email = ? and productId = ?",
                email, wishListDTO.getProductId(), wishListDTO.getCount(), email, wishListDTO.getProductId());
        return update != 0;
    }

    @Override
    public List<WishList> getMyWishlists(String email) {
        return jdbcTemplate.query("select * from wishlist where email = ?", wishlistRowMapper(), email);
    }

    private RowMapper<WishList> wishlistRowMapper() {
        return (rs, rowNum) -> {
            WishList wishlist = new WishList();
            wishlist.setEmail(rs.getString("email"));
            wishlist.setProductId(rs.getLong("productId"));
            wishlist.setCount(rs.getInt("count"));
            return wishlist;
        };
    }
}
