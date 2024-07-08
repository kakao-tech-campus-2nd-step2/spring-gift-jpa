package gift.repository;

import gift.exception.ForeignKeyConstraintViolationException;
import gift.exception.NotFoundElementException;
import gift.model.Product;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import javax.sql.DataSource;
import java.util.List;

@Repository
public class ProductJDBCRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ProductJDBCRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("image_url")
    );

    public Product save(Product product) {
        var id = insertAndReturnId(product);
        return createProductWithId(id, product);
    }

    public void update(Product product) {
        var sql = "update product set name = ?, price = ?, image_url = ? where id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
    }

    public Product findById(Long id) {
        var sql = "select id, name, price, image_url from product where id = ?";
        try {
            var product = jdbcTemplate.queryForObject(sql, productRowMapper, id);
            return product;
        } catch (EmptyResultDataAccessException exception) {
            throw new NotFoundElementException(exception.getMessage());
        }
    }

    public List<Product> findAll() {
        var sql = "select id, name, price, image_url from product";
        var products = jdbcTemplate.query(sql, productRowMapper);
        return products;
    }

    public void deleteById(Long id) {
        try {
            var sql = "delete from product where id = ?";
            jdbcTemplate.update(sql, id);
        } catch (DataIntegrityViolationException exception) {
            throw new ForeignKeyConstraintViolationException(exception.getMessage());
        }
    }

    private Long insertAndReturnId(Product product) {
        var param = new BeanPropertySqlParameterSource(product);
        return jdbcInsert.executeAndReturnKey(param).longValue();
    }

    private Product createProductWithId(Long id, Product product) {
        return new Product(id, product.getName(), product.getPrice(), product.getImageUrl());
    }
}
