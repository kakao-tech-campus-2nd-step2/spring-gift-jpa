package gift.infra;

import gift.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public List<Product> getProducts() {
        String sql = "SELECT * FROM Product";
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product getProductById(long id) {
        String sql = "SELECT * FROM Product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, new ProductRowMapper());
    }

    public void addProduct(Product product) {
        String sql = "INSERT INTO Product (name, price, imageUrl) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.name(), product.price(), product.imageUrl());
    }

    public void deleteProduct(long id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateProduct(long id, Product product) {
        String sql = "UPDATE Product SET name=? WHERE id = ?";
        jdbcTemplate.update(sql, product.name(), id);
    }
}
