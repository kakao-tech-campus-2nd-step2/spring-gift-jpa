package gift.repository;

import gift.controller.dto.ProductDTO;
import gift.domain.Product;
import gift.utils.error.ProductNotFoundException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getDouble("price"),
        rs.getString("image_url")
    );

    public Product findById(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, productRowMapper, id);
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public boolean create(Product product) {
        String sql = "INSERT INTO products (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(),
            product.getImageUrl());
        return true;
    }

    public boolean update(Product product, Long id) {
        String sql = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(),
            product.getImageUrl(), id);
        return true;
    }

    public boolean delete(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return true;
    }

    public boolean checkexist(Long id){
        String checkProductSql = "SELECT COUNT(*) FROM products WHERE id = ?";
        int count = jdbcTemplate.queryForObject(checkProductSql, Integer.class, id);
        if (count == 0) {
            throw new ProductNotFoundException("Product with id " + id + " not found");
        }
        return true;
    }
}
