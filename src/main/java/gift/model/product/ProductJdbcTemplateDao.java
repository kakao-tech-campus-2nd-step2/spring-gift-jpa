package gift.model.product;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
@Primary
public class ProductJdbcTemplateDao implements ProductDao {

    private static final String SQL_INSERT = "INSERT INTO products(name, price, image_url) VALUES (?, ?, ?)";
    private static final String SQL_SELECT_BY_ID = "SELECT id, name, price, image_url FROM products WHERE id = ?";
    private static final String SQL_SELECT_ALL = "SELECT id, name, price, image_url FROM products";
    private static final String SQL_DELETE_BY_ID = "DELETE FROM products WHERE id = ?";
    private static final String SQL_UPDATE = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?";
    private static final String SQL_SELECT_PAGING = "SELECT id, name, price, image_url FROM products LIMIT ? OFFSET ?";
    private static final String SQL_COUNT = "SELECT COUNT(*) FROM products";


    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Product> productRowMapper = new ProductRowMapper();

    public ProductJdbcTemplateDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void insert(Product product) {
        jdbcTemplate.update(SQL_INSERT, product.getName(), product.getPrice(),
            product.getImageUrl());
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            Product product = jdbcTemplate.queryForObject(SQL_SELECT_BY_ID,
                productRowMapper, id);
            return Optional.of(product);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(SQL_SELECT_ALL,
            productRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update(SQL_DELETE_BY_ID, id);
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(SQL_UPDATE, product.getName(), product.getPrice(),
            product.getImageUrl(), product.getId());
    }

    @Override
    public List<Product> findPaging(int page, int size) {
        int offset = (page) * size;
        return jdbcTemplate.query(SQL_SELECT_PAGING, productRowMapper, size, offset);
    }

    @Override
    public Long count() {
        return jdbcTemplate.queryForObject(SQL_COUNT, Long.class);
    }
}
