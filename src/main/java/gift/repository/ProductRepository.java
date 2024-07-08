package gift.repository;

import gift.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void clear() {
        String sql = "DELETE FROM product";

        jdbcTemplate.update(sql);
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM product";

        return jdbcTemplate.query(sql, (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
        ));
    }

    public Optional<Product> findById(Long productId) {
        String sql = "SELECT * FROM product WHERE id = ?";

        try {
            Product product = jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url")
                ),
                productId
            );
            return Optional.of(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void save(Product product) {
        String sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void edit(Long productId, Product request) {
        String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, request.getName(), request.getPrice(), request.getImageUrl(),
            productId);
    }

    public void deleteById(Long productId) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, productId);
    }

}
