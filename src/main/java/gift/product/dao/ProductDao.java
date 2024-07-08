package gift.product.dao;

import gift.product.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class ProductDao implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO product (name, price, image_url) VALUES(?, ?, ?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(sql, new String[]{"id"});
            statement.setString(1, product.getName());
            statement.setInt(2, product.getPrice());
            statement.setString(3, product.getImageUrl());
            return statement;
        }, keyHolder);

        return new Product(
                keyHolder.getKey()
                         .longValue(),
                product.getName(),
                product.getPrice(),
                product.getImageUrl()
        );
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";

        return jdbcTemplate.query(
                        sql,
                        (rs, rowNum) -> new Product(
                                rs.getLong("id"),
                                rs.getString("name"),
                                rs.getInt("price"),
                                rs.getString("image_url")
                        ),
                        id
                )
                .stream()
                .findFirst();
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";

        return jdbcTemplate.query(sql,
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                )
        );
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public void deleteAll() {
        String sql = "TRUNCATE TABLE product";
        jdbcTemplate.update(sql);
    }

    @Override
    public int update(Long id, Product product) {
        String sql = """
                UPDATE product
                SET name = ?, price = ?, image_url = ?
                WHERE id = ?
                """;

        return jdbcTemplate.update(
                sql,
                product.getName(),
                product.getPrice(),
                product.getImageUrl(),
                id
        );
    }

}
