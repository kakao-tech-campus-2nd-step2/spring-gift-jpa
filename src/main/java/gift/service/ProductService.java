package gift.service;

import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product.Builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .price(rs.getInt("price"))
            .imageUrl(rs.getString("imageUrl"))
            .build();

    public ProductService(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public List<Product> getAllProducts() {
        return jdbcTemplate.query("SELECT * FROM product", productRowMapper);
    }

    public void addProduct(Product product) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("imageUrl", product.getImageUrl());
        simpleJdbcInsert.execute(parameters);
    }

    public void updateProduct(Long id, Product updatedProduct) {
        jdbcTemplate.update("UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?",
                updatedProduct.getName(), updatedProduct.getPrice(), updatedProduct.getImageUrl(), id);
    }

    public void deleteProduct(Long id) {
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }

    public Product getProductById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?", productRowMapper, id);
    }
}
