package gift.domain.wishlist.dao;

import gift.domain.product.dao.ProductDao;
import gift.domain.product.entity.Product;
import gift.domain.user.entity.User;
import gift.domain.wishlist.dto.WishItemDto;
import gift.domain.wishlist.entity.WishItem;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class WishlistDao {

    private final JdbcClient jdbcClient;
    private final ProductDao productDao;

    public WishlistDao(JdbcClient jdbcClient, ProductDao productDao) {
        this.jdbcClient = jdbcClient;
        this.productDao = productDao;
    }

    public WishItem insert(WishItem wishItem) {
        String sql = "INSERT INTO wishlist (user_id, product_id) VALUES (?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
            .param(wishItem.getUserId())
            .param(wishItem.getProductId())
            .update(keyHolder);

        wishItem.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return wishItem;
    }

    public List<WishItem> findAll(User user) {
        String sql = "SELECT * FROM wishlist WHERE user_id = ?";

        return jdbcClient.sql(sql)
            .param(user.getId())
            .query(WishItemDto.class)
            .stream()
            .map(wishItemDto -> {
                Product product = productDao.findById(wishItemDto.productId()).orElse(null);
                return wishItemDto.toWishItem(user, product);
            }).toList();
    }

    public Integer delete(long wishlistId) {
        String sql = "DELETE FROM wishlist WHERE id = ?";

        return jdbcClient.sql(sql)
            .param(wishlistId)
            .update();
    }
}
