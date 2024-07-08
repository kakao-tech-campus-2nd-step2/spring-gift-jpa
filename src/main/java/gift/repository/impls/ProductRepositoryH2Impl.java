package gift.repository.impls;

import gift.domain.Product;
import gift.repository.ProductRepository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepositoryH2Impl implements ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepositoryH2Impl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Long save(Product product) {
        jdbcTemplate.update("INSERT INTO products(name, price, imageUrl) VALUES (?,?,?)",
                product.getName(), product.getPrice(), product.getImageUrl());
        Long id = jdbcTemplate.queryForObject("SELECT MAX(id) FROM products", Long.class);
        return id;
    }

    @Override
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

    @Override
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

    @Override
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

    @Override
    public Long delete(Long id) {
        var sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return id;
    }

    @Override
    public Product update(Long id, Product product) {
        jdbcTemplate.update("UPDATE products SET name = ?, price = ?, imageUrl = ? WHERE id = ?",
                product.getName(), product.getPrice(), product.getImageUrl(), id);
        return product;
    }

}
