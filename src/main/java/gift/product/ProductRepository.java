package gift.product;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert insertProduct;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertProduct = new SimpleJdbcInsert(dataSource)
            .withTableName("products")
            .usingGeneratedKeyColumns("id");
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", new BeanPropertyRowMapper<>(Product.class));
    }

    public Product findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", new BeanPropertyRowMapper<>(Product.class), id);
    }

    public long save(Product product) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("image_url", product.getImageUrl());
        return insertProduct.executeAndReturnKey(parameters).longValue();
    }

    public int update(Product product) {
        return jdbcTemplate.update("UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?",
            product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    }
}