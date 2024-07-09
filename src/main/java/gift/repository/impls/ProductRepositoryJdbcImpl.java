package gift.repository.impls;

import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class ProductRepositoryJdbcImpl {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryJdbcImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long save(Product product) {
        jdbcTemplate.update("INSERT INTO products(name, price, imageUrl) VALUES (?,?,?)",
                product.getName(), product.getPrice(), product.getImageUrl());
        Long id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM products", Long.class);
        return id;
    }

    public Optional<Product> findById(Long id) {
        var sql = "SELECT * FROM products WHERE id = ?";
        List<Product> products = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl")
                ),
                id
        );
        return products.stream().findFirst();
    }

    public Optional<Product> findByName(String name) {
        var sql = "SELECT * FROM products WHERE name = ?";
        List<Product> products = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl")
                ),
                name
        );
        return products.stream().findFirst();
    }

    public Optional<List<Product>> findByIds(List<Long> ids) {
        String sql = "SELECT * FROM products WHERE id IN (" + ids.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + ")";

        List<Product> products = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl")
                )
        );
        if(products.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(products);
    }

    public List<Product> findAll() {
        var sql = "SELECT * FROM products";
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

    public Long delete(Long id) {
        var sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return id;
    }


    public Product update(Long id, Product product) {
        jdbcTemplate.update("UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?",
                product.getName(), product.getPrice(), product.getImageUrl(), id);
        return product;
    }

}
