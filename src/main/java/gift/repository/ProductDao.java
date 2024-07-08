package gift.repository;

import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void insert(Product product) {
        String sql = "INSERT INTO products (id, name, price, imageUrl) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public Optional<Product> find(Long id) {
        String sql = "SELECT id, name, price, imageUrl from products WHERE id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, productRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Product> findAll() {
        String sql = "SELECT id, name, price, imageUrl from products";
        List<Product> products = jdbcTemplate.query(sql, productRowMapper());
        return products;
    }

    public void update(Long id, Product product) {
        String sql = "UPDATE products SET name=?, price=?, imageUrl=? where id=?";
        jdbcTemplate.update(sql,
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                id
        );
    }

    public void delete(Long id) {
        String sql = "DELETE from products where id=?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("imageUrl")
            );
            return product;
        };
    }
}
