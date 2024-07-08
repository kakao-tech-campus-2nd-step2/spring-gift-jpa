package gift.dao;

import gift.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", (rs, rowNum) ->
                new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("price"),
                        rs.getString("imageUrl")
                ));
    }

    public Product findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM product WHERE id = ?", new Object[]{id}, (rs, rowNum) ->
                new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getLong("price"),
                        rs.getString("imageUrl")
                ));
    }

    public void save(Product product) {
        jdbcTemplate.update("INSERT INTO product (name, price, imageUrl) VALUES (?, ?, ?)",
                product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void update(Long id, Product product) {
        jdbcTemplate.update("UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?",
                product.getName(), product.getPrice(), product.getImageUrl(), id);
    }

    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }
}
