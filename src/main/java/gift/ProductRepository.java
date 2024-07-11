package gift;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepository(@NonNull JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            return new Product.ProductBuilder()
                .id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .price(resultSet.getBigDecimal("price"))
                .description(resultSet.getString("description"))
                .imageUrl(resultSet.getString("imageUrl"))
                .build();
        }
    }

    public List<Product> findAll() {
        return jdbcTemplate.query("SELECT * FROM product", new ProductRowMapper());
    }

    public Product findById(Long id) {
        return jdbcTemplate.query(
            "SELECT * FROM product WHERE id = ?",
            new Object[]{id},
            resultSet -> {
                if (resultSet.next()) {
                    return new Product.ProductBuilder()
                        .id(resultSet.getLong("id"))
                        .name(resultSet.getString("name"))
                        .price(resultSet.getBigDecimal("price"))
                        .description(resultSet.getString("description"))
                        .imageUrl(resultSet.getString("imageUrl"))
                        .build();
                }
                return null;
            }
        );
    }

    public void save(@NonNull Product product) {
        jdbcTemplate.update("INSERT INTO product (name, price, description, imageUrl) VALUES (?, ?, ?, ?)",
            product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl());
    }

    public void update(@NonNull Product product) {
        jdbcTemplate.update("UPDATE product SET name = ?, price = ?, description = ?, imageUrl = ? WHERE id = ?",
            product.getName(), product.getPrice(), product.getDescription(), product.getImageUrl(), product.getId());
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }
}
