package gift.wishlist;

import gift.product.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class WishlistRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishlistRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getAllWishlists(String email) {
        return jdbcTemplate.query(
            """
                    SELECT * FROM PRODUCT p
                    JOIN WISHLIST w ON p.ID = w.PRODUCT_ID
                    WHERE w.MEMBER_EMAIL = ?
                """,
            (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl")
            ),
            email
        );
    }

    public void addWishlist(Wishlist wishlist) {
        jdbcTemplate.update(
            "INSERT INTO WISHLIST(PRODUCT_ID, MEMBER_EMAIL) VALUES (?, ?)",
            wishlist.productId(), wishlist.memberEmail()
        );
    }

    public void deleteWishlist(Wishlist wishlist) {
        jdbcTemplate.update(
            "DELETE FROM WISHLIST WHERE MEMBER_EMAIL = ? AND PRODUCT_ID = ?",
            wishlist.memberEmail(), wishlist.productId()
        );
    }

    public Boolean existWishlist(Wishlist wishlist) {
        return jdbcTemplate.queryForObject(
            "SELECT EXISTS (SELECT * FROM WISHLIST WHERE PRODUCT_ID = ? AND MEMBER_EMAIL = ?)",
            Boolean.class,
            wishlist.productId(), wishlist.memberEmail()
        );
    }
}
