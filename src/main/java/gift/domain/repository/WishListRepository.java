package gift.domain.repository;

import gift.domain.model.WishResponseDto;
import gift.domain.model.WishUpdateRequestDto;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<WishResponseDto> getProductsByUserEmail(String email) {
        String sql = "SELECT w.id, w.count, p.id as product_id, p.name as product_name, " +
            "p.price as product_price, p.imageurl as product_image_url " +
            "FROM wishlists w " +
            "JOIN products p ON w.product_id = p.id " +
            "WHERE w.user_email = ?";

        return jdbcTemplate.query(sql, new Object[]{email}, (rs, rowNum) ->
            new WishResponseDto(
                rs.getLong("id"),
                rs.getInt("count"),
                rs.getLong("product_id"),
                rs.getString("product_name"),
                rs.getLong("product_price"),
                rs.getString("product_image_url")
            )
        );
    }

    public void addWish(String email, Long productId) {
        jdbcTemplate.update(
            "INSERT INTO wishlists (user_email, product_id) VALUES (?, ?)",
            email,
            productId
        );
    }

    public boolean isExistWish(String email, Long productId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(
            "SELECT EXISTS(SELECT 1 FROM wishlists WHERE user_email = ? AND product_id = ?)",
            Boolean.class,
            email,
            productId
        ));
    }

    public void deleteWishProduct(String email, Long productId) {
        jdbcTemplate.update(
            "DELETE FROM wishlists WHERE user_email = ? AND product_id = ?",
            email,
            productId
        );
    }

    public void updateWishProduct(String email, WishUpdateRequestDto wishUpdateRequestDto) {
        jdbcTemplate.update(
            "UPDATE wishlists SET count = ? WHERE user_email = ? AND product_id = ?",
            wishUpdateRequestDto.getCount(),
            email,
            wishUpdateRequestDto.getProductId()
        );
    }
}
