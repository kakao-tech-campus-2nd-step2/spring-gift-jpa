package gift.product.infra;

import gift.product.domain.Product;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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

    public Long addProduct(Product product) {
        String sql = "INSERT INTO Product (name, price, imageUrl) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, product.name(), product.price(), product.imageUrl());
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setDouble(2, product.getPrice());
            ps.setString(3, product.getImageUrl());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public void deleteProduct(long id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateProduct(long id, Product product) {
        String sql = "UPDATE Product SET name=? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), id);
    }
}
