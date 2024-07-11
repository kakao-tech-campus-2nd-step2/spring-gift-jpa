package gift.repository;

import gift.domain.product.Product;
import gift.domain.product.ProductRequest;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public List<Product> findAll() {
        String sql = "SELECT * FROM Product";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("price"),
                rs.getString("imageUrl")
            )
        );
    }

    public Product findById(long id) {
        String sql = "SELECT * FROM Product WHERE id = ?";
        return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getLong("price"),
                rs.getString("imageUrl")
            ),
            id
        );
    }

    public Product insert(ProductRequest productRequest) {
        String sql = "INSERT INTO Product (name, price, imageUrl) VALUES(?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, productRequest.name());
            preparedStatement.setLong(2, productRequest.price());
            preparedStatement.setString(3, productRequest.imageUrl());
            return preparedStatement;
        }, keyHolder);
        return findById((Long) keyHolder.getKey());
    }

    public Product update(long id, ProductRequest productRequest) {
        String sql = "UPDATE Product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, productRequest.name(), productRequest.price(), productRequest.imageUrl(), id);
        return findById(id);
    }

    public void delete(long id) {
        String sql = "DELETE FROM Product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
