package gift.service;

import gift.model.Product;
import gift.model.ProductDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;

@Repository
@Validated
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProduct(@Valid ProductDTO productDTO) {
        String sql = "INSERT INTO product (id, name, price, imageUrl) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, productDTO.id(), productDTO.name(), productDTO.price(),
            productDTO.imageUrl());
    }

    public Product getProductById(long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<>(Product.class), id);
    }

    public List<Product> getAllProduct() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<>(Product.class));
    }

    public boolean updateProduct(Long id, @Valid ProductDTO updatedDTO) {
        String sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        return jdbcTemplate.update(sql, updatedDTO.name(), updatedDTO.price(),
            updatedDTO.imageUrl(), id) > 0;

    }

    public boolean deleteProduct(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        return jdbcTemplate.update(sql, id) > 0;
    }

}