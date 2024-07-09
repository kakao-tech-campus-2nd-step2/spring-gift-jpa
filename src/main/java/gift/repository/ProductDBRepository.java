package gift.repository;

import gift.dto.request.ProductRequest;
import gift.domain.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.*;

@Repository
public class ProductDBRepository implements ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductDBRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Product save(Product product) {
        String sql = "INSERT INTO product(name, price, imageUrl) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, product.getName());
            ps.setLong(2, product.getPrice());
            ps.setString(3, product.getImageUrl());
            return ps;
        }, keyHolder);

        product.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return product;
    }

    @Override
    public Optional<Product> findById(Long id) {
        String sql = "SELECT * FROM product WHERE id = ?";
        List<Product> products = jdbcTemplate.query(sql, productRowMapper(), id);
        return products.stream().findAny();
    }

    @Override
    public Optional<Product> findByName(String name) {
        String sql = "SELECT * FROM product WHERE name = ?";
        List<Product> products = jdbcTemplate.query(sql, productRowMapper(), name);
        return products.stream().findAny();
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.query("select * from product", productRowMapper());
    }

    @Override
    public Optional<Product> updateById(Long id, ProductRequest productRequest){
        String sql  = "UPDATE product " +
                "SET name = ?,price=?,imageUrl=? " +
                "WHERE id = ?";
        jdbcTemplate.update(sql,productRequest.getName(), productRequest.getPrice(), productRequest.getImageUrl(), id);
        return findById(id);
    }

    @Override
    public Optional<Product> deleteById(Long id) {
        Optional<Product> deleted_product = findById(id);
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
        return deleted_product;
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> new Product(
               rs.getLong("id"),
               rs.getString("name"),
               rs.getLong("price"),
               rs.getString("imageUrl")
       );
    }
}
