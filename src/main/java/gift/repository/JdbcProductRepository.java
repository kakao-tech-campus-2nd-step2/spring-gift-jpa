package gift.repository;

import gift.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(
                "SELECT id, name, description, price, image_url FROM product",
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                )
        );
    }

    @Override
    public Product findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, name, description, price, image_url FROM product WHERE id = ?",
                new Object[]{id},
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                )
        );
    }

    @Override
    public void save(Product product) {
        jdbcTemplate.update(
                "INSERT INTO product (id, name, description, price, image_url) VALUES (?, ?, ?, ?, ?)",
                product.getId(), product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl()
        );
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(
                "UPDATE product SET name = ?, description = ?, price = ?, image_url = ? WHERE id = ?",
                product.getName(), product.getDescription(), product.getPrice(), product.getImageUrl(), product.getId()
        );
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(
                "DELETE FROM product WHERE id = ?",
                id
        );
    }
}

