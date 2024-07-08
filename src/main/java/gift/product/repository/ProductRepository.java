package gift.product.repository;

import gift.product.model.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName("product")
            .usingGeneratedKeyColumns("id");
    }

    public Optional<Product> findById(long id) {
        List<Product> products = jdbcTemplate.query("SELECT * FROM product WHERE id = ?",
            new Object[]{id}, (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("imgUrl")
            ));
        if (products.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(products.get(0));
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product",
            (resultSet, rowNum) -> new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("imgUrl")
                ));
    }

    public Product save(Product product) {
        if (product.getId() == 0) {
            Number newId = simpleJdbcInsert.executeAndReturnKey(new MapSqlParameterSource()
                .addValue("name", product.getName())
                .addValue("price",product.getPrice())
                .addValue("imgUrl",product.getImgUrl()));
            product.setId(newId.longValue());
            return product;
        }
        jdbcTemplate.update("UPDATE product SET name = ?, price = ?, imgUrl = ? WHERE id = ?",
            product.getName(), product.getPrice(), product.getImgUrl(), product.getId());
        return product;
    }

    public void deleteById(long id) {
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }
}
