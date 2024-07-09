package gift.model.dao;

import gift.model.Product;
import gift.model.repository.ProductRepository;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

public class ProductDao implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Product entity) {
        if (entity.isNew()) {
            jdbcTemplate.update(ProductQuery.INSERT_PRODUCT.getQuery(), entity.getName(), entity.getPrice(),
                    entity.getImgUrl(), entity.isDeleted());
            return;
        }
        update(entity);
    }

    @Override
    public void update(Product entity) {
        jdbcTemplate.update(ProductQuery.UPDATE_PRODUCT.getQuery(), entity.getName(), entity.getPrice(),
                entity.getImgUrl(), entity.isDeleted(), entity.getId());
    }

    @Override
    public Optional<Product> find(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(
                    ProductQuery.SELECT_PRODUCT_BY_ID.getQuery(), new Object[]{id}, (rs, rowNum) -> ProductMapper(rs)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Product entity) {
        jdbcTemplate.update(ProductQuery.DELETE_PRODUCT.getQuery(), entity.getId());
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query(ProductQuery.SELECT_ALL_PRODUCTS.getQuery(), (rs, rowNum) -> ProductMapper(rs));
    }

    private Product ProductMapper(ResultSet rs) throws SQLException {
        return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("image_url"),
                rs.getBoolean("is_deleted"));
    }
}
