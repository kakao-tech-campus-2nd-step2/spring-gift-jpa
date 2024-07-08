package gift.product;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;
    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("Products")
                .usingGeneratedKeyColumns("id");
    }

    public Product insertProduct(ProductDTO product){
        Map<String, Object> parameters = new HashMap<String, Object>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("imageUrl", product.getImageUrl());
        Long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new Product(id, product.getName(), product.getPrice(), product.getImageUrl());
    }
    public Product selectProduct(Long id) {
        var sql = "select id, name, price, imageUrl from Products where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                getProductRowMapper(),
                id
        );
    }

    public List<Product> selectProducts(){
        var sql = "select id, name, price, imageUrl from Products";
        return jdbcTemplate.query(
                sql,
                getProductRowMapper()
        );
    }

    private static RowMapper<Product> getProductRowMapper() {
        return (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getLong("price"),
                resultSet.getString("imageUrl")
        );
    }

    public Product updateProduct(Long id, ProductDTO updateParam){
        var sql = "update Products set name=?, price=?, imageUrl=? where id = ?";
        jdbcTemplate.update(sql,
                updateParam.getName(),
                updateParam.getPrice(),
                updateParam.getImageUrl(),
                id);
        return new Product(id, updateParam.getName(), updateParam.getPrice(), updateParam.getImageUrl());
    }

    public void deleteProduct(Long id){
        var sql = "delete from Products where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
