package gift.repository;

import gift.model.WishList;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class WishListRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<WishList> wishListRowMapper = (rs, rowNum) -> {
        WishList wishList = new WishList();
        wishList.setUserEmail(rs.getString("user_email"));
        wishList.setProductId(rs.getLong("product_id"));
        return wishList;
    };

    public WishListRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<WishList> findByUserEmail(String email) {
        return jdbcTemplate.query("SELECT * FROM wishlist WHERE user_email = ?", wishListRowMapper, email);
    }

    public Optional<WishList> findByUserEmailAndProductId(String email, Long productId) {
        List<WishList> results = jdbcTemplate.query("SELECT * FROM wishlist WHERE user_email = ? AND product_id = ?", wishListRowMapper, email, productId);
        return results.stream().findFirst();
    }

    public Optional<WishList> findById(Long id) {
        List<WishList> results = jdbcTemplate.query("SELECT * FROM wishlist WHERE id = ?", wishListRowMapper, id);
        return results.stream().findFirst();
    }

    public void save(WishList wishList) {
        jdbcTemplate.update("INSERT INTO wishlist (user_email, product_id) VALUES (?, ?)", wishList.getUserEmail(), wishList.getProductId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM wishlist WHERE id = ?", id);
    }

    public void deleteByUserEmailAndProductId(String email, Long productId) {
        jdbcTemplate.update("DELETE FROM wishlist WHERE user_email = ? AND product_id = ?", email, productId);
    }

}
