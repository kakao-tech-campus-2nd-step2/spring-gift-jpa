package gift.repository;

import gift.model.Name;
import gift.model.Product;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
        rs.getLong("id"),
        new Name(rs.getString("name")),
        rs.getInt("price"),
        rs.getString("image_url") // image_url 컬럼 사용
    );

    public List<Product> findAll() {
        String sql = "SELECT id, name, price, image_url FROM products"; // image_url 컬럼 사용
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public void add(Product product) {
        String sql = "INSERT INTO products (id, name, price, image_url) VALUES (?, ?, ?, ?)"; // image_url 컬럼 사용
        jdbcTemplate.update(sql, product.getId(), product.getName().getName(), product.getPrice(), product.getImageUrl());
    }

    public Product findById(Long id) {
        String sql = "SELECT id, name, price, image_url FROM products WHERE id = ?"; // image_url 컬럼 사용
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, productRowMapper);
    }

    public void update(Long id, Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?"; // image_url 컬럼 사용
        jdbcTemplate.update(sql, product.getName().getName(), product.getPrice(), product.getImageUrl(), id);
    }

    public void delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}