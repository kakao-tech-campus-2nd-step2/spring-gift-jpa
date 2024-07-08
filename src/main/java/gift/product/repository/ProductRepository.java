package gift.product.repository;

import gift.product.dto.LoginMember;
import gift.product.model.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("Product")
            .usingGeneratedKeyColumns("id");
    }

    public Product save(Product product) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", product.getName());
        params.put("price", product.getPrice());
        params.put("imageUrl", product.getImageUrl());

        Long productId = (Long) simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource(params));
        return new Product(productId, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public List<Product> findAll() {
        var sql = "SELECT * FROM Product";

        return jdbcTemplate.query(sql, getProductRowMapper());
    }

    public Product findById(Long id) throws DataAccessException {
        var sql = "SELECT id, name, price, imageUrl FROM Product WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, getProductRowMapper(), id);
    }

    public void update(Product product) {
        var sql = "UPDATE Product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";

        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());
    }

    public void delete(Long id) {
        var sql = "DELETE FROM Product WHERE id = ?";

        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> new Product(
            resultSet.getLong("id"),
            resultSet.getString("name"),
            resultSet.getInt("price"),
            resultSet.getString("imageUrl")
        );
    }
}
