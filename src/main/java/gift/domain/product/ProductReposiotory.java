package gift.domain.product;

import gift.web.exception.ProductNotFoundException;
import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductReposiotory {
    private JdbcTemplate jdbcTemplate;

    public ProductReposiotory(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> new Product(
            rs.getLong("id"),
            rs.getString("name"),
            rs.getLong("price"),
            rs.getString("image_url")
        );
    }

    public Product insertProduct(Product product) {
        var sql = "insert into products (name, price, image_url) values (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection-> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, product.name());
            ps.setDouble(2, product.price());
            ps.setString(3, product.imageUrl());
            return ps;
        }, keyHolder);

        Product newProduct = new Product(keyHolder.getKey().longValue(), product.name(), product.price(), product.imageUrl());
        return newProduct;
    }

    public List<Product> selectAllProducts() {
        var sql = "select * from products";
        return jdbcTemplate.query(sql, productRowMapper());
    }

    public Product selectProductById(long id) {
        var sql = "select * from products where id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, productRowMapper(), id);
        } catch (EmptyResultDataAccessException e) {
            throw new ProductNotFoundException("상품이 존재하지 않습니다.");
        }
    }

    public void updateProduct(Product product) {
        var sql = "update products set name = ?, price = ?, image_url = ? where id = ?";
        jdbcTemplate.update(
            sql,
            product.name(),
            product.price(),
            product.imageUrl(),
            product.id()
        );
    }

    public void deleteProductById(long id) {
        var sql = "delete from products where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
