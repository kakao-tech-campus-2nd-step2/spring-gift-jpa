package gift.repository;

import gift.domain.Product;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Product selectOneProduct(Long id) {
        String sql = "SELECT id, name, price, imageUrl FROM products WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        return namedParameterJdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(Product.class));
    }

    public List<Product> selectAllProducts() {
        String sql = "SELECT id, name, price, imageUrl FROM products";
        return namedParameterJdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public void insertProduct(Product product) {
        String sql = "INSERT INTO products (id, name, price, imageUrl) VALUES (:id, :name, :price, :imageUrl)";
        var params = new BeanPropertySqlParameterSource(product);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void deleteProduct(Long id) {
        String sql = "DELETE FROM products WHERE id = :id";
        var params = new MapSqlParameterSource("id", id);
        namedParameterJdbcTemplate.update(sql, params);
    }

    public void updateProduct(Product product) {
        String sql = "UPDATE products SET name = :name, price = :price, imageUrl = :imageUrl WHERE id = :id";
        var params = new BeanPropertySqlParameterSource(product);
        namedParameterJdbcTemplate.update(sql, params);
    }
}
