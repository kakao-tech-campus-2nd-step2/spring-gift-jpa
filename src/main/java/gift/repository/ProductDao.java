package gift.repository;

import gift.domain.Product;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
    }

    public List<Product> findAll() {
        var sql = "select * from product";
        return jdbcTemplate.query(
            sql,
            (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("imageUrl")
            )
        );
    }

    public Product findById(Long id) {
        var sql = "SELECT * FROM product WHERE id = ?";
        return jdbcTemplate.queryForObject(
            sql,
            (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("imageUrl")
            ),
            id);
    }

    public Long insertProduct(Product product) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("imageUrl", product.getImageUrl());

        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return newId.longValue();
    }

    public Long updateProduct(Long id, Product product) {
        var sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
        return id;
    }

    public Long deleteById(Long id) {
        var sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return id;
    }

}
