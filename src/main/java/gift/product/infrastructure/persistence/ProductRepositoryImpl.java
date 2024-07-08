package gift.product.infrastructure.persistence;

import gift.core.domain.product.Product;
import gift.core.domain.product.ProductRepository;
import jakarta.annotation.Nonnull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryImpl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> rowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
    );

    @Autowired
    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product get(Long id) {
        String sql = "SELECT * FROM `products` WHERE `id` = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

    @Override
    public boolean exists(Long id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM `products` WHERE `id` = ?)";
        Boolean result = jdbcTemplate.queryForObject(sql, Boolean.class, id);
        return result != null && result;
    }

    @Override
    public void save(@Nonnull Product product) {
        String sql = """
                MERGE INTO products AS target
                USING (VALUES (?, ?, ?, ?)) AS source (id, name, price, image_url)
                ON target.id = source.id
                WHEN MATCHED THEN
                UPDATE SET target.name = source.name, target.price = source.price, target.image_url = source.image_url
                WHEN NOT MATCHED THEN
                INSERT (name, price, image_url) VALUES (source.name, source.price, source.image_url);
        """;
        jdbcTemplate.update(sql, product.id(), product.name(), product.price(), product.imageUrl());
    }

    @Override
    public int size() {
        String sql = "SELECT COUNT(*) FROM `products`";
        Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
        if (result == null) {
            return 0;
        }
        return result;
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM `products`";
        List<Product> products = jdbcTemplate.query(sql, rowMapper);
        return List.copyOf(products);
    }

    @Override
    public void remove(Long id) {
        String sql = "DELETE FROM `products` WHERE `id` = ?";
        jdbcTemplate.update(sql, id);
    }
}
