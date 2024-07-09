package gift.repositories;

import gift.Product;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAll() {
        List<Product> result = new ArrayList<>();
        List<Product> queried = jdbcTemplate.query("SELECT * FROM products", (resultSet, rowNum) ->
            new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getFloat("price"),
                resultSet.getString("imageUrl")
            )
        );
        if (queried != null) {
            result.addAll(queried);
        }
        return result;
    }

    public Product find(long id) {
        List<Product> queried = jdbcTemplate.query("SELECT * FROM products where id=" + id + " limit 1", (resultSet, rowNum) ->
            new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getFloat("price"),
                resultSet.getString("imageUrl")
            )
        );
        if (queried.size() == 0) {
            return null;
        }
        return queried.get(0);
    }

    public void insert(Product product) {
        jdbcTemplate.execute("INSERT INTO products (id, name, price, imageUrl) VALUES("
            + product.getId() + ", '"
            + product.getName() + "', "
            + product.getPrice() + ", '"
            + product.getImageUrl() + "');"
        );
    }

    public void update(Product product) {
        jdbcTemplate.execute("UPDATE products set "
            + "name='" + product.getName() + "', "
            + "price=" + product.getPrice() + ", "
            + "imageUrl='" + product.getImageUrl() + "' "
            + "WHERE id=" + product.getId() + ";"
        );
    }

    public void remove(Long id) {
        jdbcTemplate.execute("DELETE products WHERE id=" + id);
    }
}
