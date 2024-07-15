package gift.repository;

import gift.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcProductRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
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
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("description", product.getDescription());
        parameters.put("price", product.getPrice());
        parameters.put("image_url", product.getImageUrl());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        product.setId(newId.longValue());
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
