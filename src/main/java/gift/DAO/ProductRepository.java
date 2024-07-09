package gift.DAO;

import gift.Entity.ProductEntity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ProductEntity> findAll() {
        String sql = "SELECT * FROM Product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(ProductEntity.class));
    }

    public ProductEntity findById(Long id) {
        String sql = "SELECT * FROM Product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(ProductEntity.class), id);
    }

    public int save(ProductEntity product) {
        String sql = "INSERT INTO Product (name, price, imageUrl) VALUES (?, ?, ?)";
        return jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public int update(ProductEntity product) {
        String sql = "UPDATE Product SET id = ?, name = ?, price = ?, imageUrl = ? WHERE id = ?";
        return jdbcTemplate.update(sql, product.getId(), product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
    }

    public int deleteById(Long id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
