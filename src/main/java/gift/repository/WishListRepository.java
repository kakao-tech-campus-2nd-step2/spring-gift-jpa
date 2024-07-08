package gift.repository;

import gift.domain.Product;
import gift.dto.WishProduct;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class WishListRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public WishListRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<Product> selectWishList(String email) {
        // get productIds
        String getIdSql = "SELECT productId FROM wishlist WHERE email = :email";
        Map<String, Object> params = new HashMap<>();
        params.put("email", email);

        List<Long> productIds = namedParameterJdbcTemplate.queryForList(getIdSql, params, Long.class);

        // get products
        String getProductSql = "SELECT * FROM products WHERE productId IN (:productIds)";
        Map<String, Object> productParams = new HashMap<>();
        productParams.put("productIds", productIds);

        return namedParameterJdbcTemplate.query(getProductSql, productParams, new BeanPropertyRowMapper<>(Product.class));
    }

    public void addWishProduct(WishProduct wishProduct) {
        String sql = "INSERT INTO wishlist (email, productId) VALUES (:email, :productId)";
        var params = new BeanPropertySqlParameterSource(wishProduct);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteProduct(WishProduct wishProduct) {
        String sql = "DELETE FROM wishlist WHERE productId = :productId AND email = :email";
        var params = new BeanPropertySqlParameterSource(wishProduct);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
