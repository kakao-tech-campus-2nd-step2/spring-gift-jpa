package gift.repository;

import gift.dto.CreateProduct;
import gift.dto.EditProduct;
import gift.entity.Product;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("products")
                .usingGeneratedKeyColumns("id");
    }

    public long insert(CreateProduct.Request request) {
        Map<String, Object> parameters = Map.of(
                "name", request.getName(),
                "url", request.getImageUrl(),
                "price", request.getPrice()
        );
        Number newId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return newId.longValue();
    }

    public List<Product> findAll() {
        var sql = "select * from products";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("url")
                )
        );
    }

    public Product findOneById(int id) {
        var sql = "select name,price,url from products where id=?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new Product(
                        id,
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("url")
                ),
                id
        );
    }

    public boolean update(int id, EditProduct.Request request) {
        try {
            var sql = "update products set name=?, price=?, url=? where id=?";
            jdbcTemplate.update(sql, request.getName(), request.getPrice(), request.getImageUrl(), id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        try {
            var sql = "delete from products where id= ?";
            jdbcTemplate.update(sql, id);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }
}
