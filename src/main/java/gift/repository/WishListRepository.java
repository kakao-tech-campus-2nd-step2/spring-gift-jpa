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

    public List<Product> selectProductsFromWishList(List<Long> productIds) {
        String getProductSql = "SELECT * FROM products WHERE id IN (:productIds)";
        Map<String, Object> productParams = new HashMap<>();
        productParams.put("productIds", productIds);

        return namedParameterJdbcTemplate.query(getProductSql, productParams, new BeanPropertyRowMapper<>(Product.class));
    }

    public List<Long> selectProductIdsFromWishList(Long memberId) {
        String getIdSql = "SELECT productId FROM wishlist WHERE memberId = :memberId";
        Map<String, Object> params = new HashMap<>();
        params.put("memberId", memberId);
        return namedParameterJdbcTemplate.queryForList(getIdSql, params, Long.class);
    }

    public void addWishProduct(WishProduct wishProduct) {
        String sql = "INSERT INTO wishlist (memberId, productId) VALUES (:memberId, :productId)";
        var params = new BeanPropertySqlParameterSource(wishProduct);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteProduct(WishProduct wishProduct) {
        String sql = "DELETE FROM wishlist WHERE productId = :productId AND memberId = :memberId";
        var params = new BeanPropertySqlParameterSource(wishProduct);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
