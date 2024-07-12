package gift.domain.repository;

import gift.domain.dto.request.ProductRequest;
import gift.domain.entity.OldProduct;
import gift.domain.entity.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

//@Repository
public class OldProductRepository {

//    private final JdbcTemplate jdbcTemplate;
//
//    public ProductRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    private RowMapper<OldProduct> getRowMapper() {
//        return (resultSet, rowNum) -> new OldProduct(
//            resultSet.getLong("id"),
//            resultSet.getString("name"),
//            resultSet.getLong("price"),
//            resultSet.getString("image_url")
//        );
//    }
//
//    public List<OldProduct> findAll() {
//        String sql = "SELECT * FROM products";
//        return jdbcTemplate.query(sql, getRowMapper());
//    }
//
//    public Optional<OldProduct> findById(Long id) {
//        try {
//            String sql = "SELECT * FROM products WHERE id = ?";
//            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getRowMapper(), id));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    public Optional<OldProduct> findByContents(ProductRequest requestDto) {
//        try {
//            String sql = "SELECT * FROM products WHERE name = ? AND price = ? AND image_url = ?";
//            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, getRowMapper(),
//                requestDto.name(),
//                requestDto.price(),
//                requestDto.imageUrl()));
//        } catch (EmptyResultDataAccessException e) {
//            return Optional.empty();
//        }
//    }
//
//    public Product update(Long id, ProductRequest requestDto) {
//        String sql = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?";
//        jdbcTemplate.update(sql, requestDto.name(), requestDto.price(), requestDto.imageUrl(), id);
//        return ProductRequest.toEntity(id, requestDto);
//    }
//
//    public Product save(ProductRequest requestDto) {
//        String sql = "INSERT INTO products (name, price, image_url) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql, requestDto.name(), requestDto.price(), requestDto.imageUrl());
//
//        // Retrieve the generated id
//        Long id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM products", Long.class);
//        return ProductRequest.toEntity(id, requestDto);
//    }
//
//    public void deleteById(Long id) {
//        String sql = "DELETE FROM products WHERE id = ?";
//        jdbcTemplate.update(sql, id);
//    }
}
