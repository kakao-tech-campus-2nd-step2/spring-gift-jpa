package gift.repository;

import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository{

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> productRowMapper;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        productRowMapper = (rs, rowNum) ->
            new Product(rs.getLong("id"),
                    rs.getString("name"),
                    rs.getLong("price"),
                    rs.getString("image_url"));

    }
    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM products", productRowMapper);
    }
    public Product findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM products WHERE id = ?", productRowMapper, id);
    }

    public void save(Product product) {
        jdbcTemplate.update("INSERT INTO products (name, price, image_url) VALUES (?, ?, ?)",
            product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void update(Long id, Product product) {
        jdbcTemplate.update("UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?",
            product.getName(), product.getPrice(), product.getImageUrl(), id);
    }
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
    }
}
