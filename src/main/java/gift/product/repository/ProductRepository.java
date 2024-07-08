package gift.product.repository;

import gift.product.model.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getLong("price"),
        rs.getString("imageUrl")
    );

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }

    public Product findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", productRowMapper, id);
    }

    public List<Product> findByCond(String name) {
        String sql = "SELECT * FROM products WHERE name LIKE ?";
        String likePattern = "%" + name + "%";
        return jdbcTemplate.query(sql, productRowMapper, likePattern);
    }

    public int save(Product product) {
        return jdbcTemplate.update(
            "INSERT INTO products (name, price, imageUrl) VALUES (?, ?, ?)",
            product.getName(), product.getPrice(), product.getImageUrl()
        );
    }

    public int update(Long id, Product product) {
        return jdbcTemplate.update(
            "UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?",
            product.getName(), product.getPrice(), product.getImageUrl(), id
        );
    }

    public int delete(Long id) {
        return jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    }

}
