package gift.entity;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product insertProduct(Product product) {
        var sql = "insert into product (name, price, image_url) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.name.getValue());
            ps.setInt(2, product.price);
            ps.setString(3, product.imageUrl);
            return ps;
        }, keyHolder);
        Long productId = keyHolder.getKey().longValue();
        return new Product(productId, product.name, product.price, product.imageUrl);
    }

    public Optional<Product> selectProduct(Long id) {
        var sql = "select id, name, price, image_url from product where id = ?";
        List<Product> products = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        new ProductName(resultSet.getString("name")),
                        resultSet.getInt("price"),
                        resultSet.getString("image_url")
                ),
                id
        );
        return products.stream().findFirst();
    }

    public List<Product> selectAllProducts() {
        var sql = "select id, name, price, image_url from product";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        new ProductName(resultSet.getString("name")),
                        resultSet.getInt("price"),
                        resultSet.getString("image_url")
                )
        );
    }

    public Product updateProduct(Product product) {
        var sql = "update product set name = ?, price = ?, image_url = ? where id = ?";
        jdbcTemplate.update(sql, product.name.getValue(), product.price, product.imageUrl, product.id);
        return product;
    }

    public void deleteProduct(Long id) {
        var sql = "delete from product where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
