package gift.repository;

import gift.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }

    public List<Product> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM products",
                (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("name"), rs.getInt("price"), rs.getString("imageUrl"))
        );
    }

    public Optional<Product> findById(long id) {
        try {
            Product product = jdbcTemplate.queryForObject(
                    "SELECT * FROM products WHERE id = ?",
                    new Object[]{id},
                    (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("name"), rs.getInt("price"), rs.getString("imageUrl"))
            );
            return Optional.of(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void save(Product product) {
        jdbcInsert.executeAndReturnKey(new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price", product.getPrice())
                .addValue("imageUrl", product.getImageUrl()));
    }

    public void update(Product product) {
        int rows = jdbcTemplate.update(
                "UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?",
                product.getName(), product.getPrice(), product.getImageUrl(), product.getId()
        );
        if (rows == 0) {
            throw new IllegalArgumentException("Product with id " + product.getId() + " not found");
        }
    }

    public void delete(Long id) {
        int rows = jdbcTemplate.update("DELETE FROM products WHERE id = ?", id);
        if (rows == 0) {
            throw new IllegalArgumentException("Product with id " + id + " not found");
        }
    }

    public void deleteBatch(List<Long> ids) {
        int rows = jdbcTemplate.update("DELETE FROM products WHERE id IN (" + String.join(",", ids.stream().map(String::valueOf).toArray(String[]::new)) + ")");
        if (rows == 0) {
            throw new IllegalArgumentException("No products found for given ids");
        }
    }
}
