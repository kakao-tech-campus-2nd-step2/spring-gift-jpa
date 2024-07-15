package gift.product.service;

import gift.product.dto.ProductDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void save(ProductDto productDto) {
        String sql = "INSERT INTO products (name, price, imgUrl) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productDto.name(), productDto.price(), productDto.imgUrl());
    }

    public List<ProductDto> findAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new ProductDto(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("imgUrl")
                ));
    }

    public ProductDto findById(Long id) {
        String sql = "SELECT * FROM products WHERE product_id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new ProductDto(
                        rs.getLong("product_id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("imgUrl")
                ));
    }

    public void update(Long id, ProductDto productDto) {
        String sql = "UPDATE products SET name = ?, price = ?, imgUrl = ? WHERE product_id = ?";
        jdbcTemplate.update(sql, productDto.name(), productDto.price(), productDto.imgUrl(), id);
    }

    public void deleteById(Long product_id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        jdbcTemplate.update(sql, product_id);
    }
}