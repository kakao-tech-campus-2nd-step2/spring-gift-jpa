package gift.repository;

import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", this::mapRowToProduct);
    }

    public Product findById(long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", this::mapRowToProduct, id);
    }

    public int save(Product product) {
        String sql = "INSERT INTO products (name, price, image_url) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public int update(Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
    }

    public int deleteById(long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }

    private Product mapRowToProduct(ResultSet rs, int rowNum) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("image_url")
        );
    }
}