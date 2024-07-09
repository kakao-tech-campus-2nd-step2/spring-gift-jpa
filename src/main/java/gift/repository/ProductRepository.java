package gift.repository;

import gift.domain.Product;
import gift.dto.ProductRequest;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Product findById(Long id) {
        try {
            String sql = "SELECT * FROM product WHERE id = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{id}, productRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Product findByName(String name) {
        try {
            String sql = "SELECT * FROM product WHERE name = ?";
            return jdbcTemplate.queryForObject(sql, new Object[]{name}, productRowMapper());
        }catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public List<Product> findAll(){
        return jdbcTemplate.query("select * from product", productRowMapper());
    }

    public Product save(ProductRequest productRequest) {
        String sql = "INSERT INTO product (name, price, imageUrl) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, productRequest.getName());
            ps.setInt(2, productRequest.getPrice());
            ps.setString(3, productRequest.getImageUrl());
            return ps;
        }, keyHolder);

        return new Product(keyHolder.getKey().longValue(), productRequest.getName(), productRequest.getPrice(),productRequest.getImageUrl());

    }

    public Product update(Product product){
        String sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), product.getId());

        return new Product(product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }
    public void delete(Long id){
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> new Product(rs.getLong("id"), rs.getString("name"), rs.getInt("price"), rs.getString("imageUrl"));
    }
}
