package gift.repository;

import gift.model.Name;
import gift.model.Product;
import gift.model.User;
import gift.model.WishList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class WishListRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public WishListRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<WishList> wishListRowMapper = (rs, rowNum) -> {
        WishList wishList = new WishList();
        wishList.setId(rs.getLong("id"));
        User user = new User();
        user.setId(rs.getLong("user_id"));
        wishList.setUser(user);
        return wishList;
    };

    public Optional<WishList> findByUser(User user) {
        String sql = "SELECT id, user_id FROM wish_lists WHERE user_id = ?";
        List<WishList> wishLists = jdbcTemplate.query(sql, new Object[]{user.getId()}, wishListRowMapper);
        if (wishLists.isEmpty()) {
            return Optional.empty();
        }
        WishList wishList = wishLists.get(0);
        String productSql = "SELECT p.id, p.name, p.price, p.image_url FROM products p JOIN wish_list_products wlp ON p.id = wlp.product_id WHERE wlp.wish_list_id = ?";
        List<Product> products = jdbcTemplate.query(productSql, new Object[]{wishList.getId()}, (rs, rowNum) -> new Product(
            rs.getLong("id"),
            new Name(rs.getString("name")),
            rs.getInt("price"),
            rs.getString("image_url")
        ));
        wishList.setProducts(products);
        return Optional.of(wishList);
    }

    public WishList save(WishList wishList) {
        if (wishList.getId() == null) {
            String sql = "INSERT INTO wish_lists (user_id) VALUES (?)";
            jdbcTemplate.update(sql, wishList.getUser().getId());
            String fetchSql = "SELECT id FROM wish_lists WHERE user_id = ?";
            Long id = jdbcTemplate.queryForObject(fetchSql, new Object[]{wishList.getUser().getId()}, Long.class);
            wishList.setId(id);
        }

        String insertSql = "INSERT INTO wish_list_products (wish_list_id, product_id) VALUES (?, ?)";
        for (Product product : wishList.getProducts()) {
            String checkSql = "SELECT COUNT(*) FROM wish_list_products WHERE wish_list_id = ? AND product_id = ?";
            int count = jdbcTemplate.queryForObject(checkSql, new Object[]{wishList.getId(), product.getId()}, Integer.class);
            if (count == 0) {
                jdbcTemplate.update(insertSql, wishList.getId(), product.getId());
            }
        }
        return wishList;
    }
}