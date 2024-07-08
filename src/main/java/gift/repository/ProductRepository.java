package gift.repository;

import gift.domain.Product;
import java.util.List;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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

    public void save(Product product) {
        String sql = "INSERT INTO product (name, price, imageUrl) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void update(Long id, Product product){
        String sql = "UPDATE product SET name = ?, price = ?, imageUrl = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }
    public void delete(Long id){
        jdbcTemplate.update("DELETE FROM product WHERE id = ?", id);
    }

    private RowMapper<Product> productRowMapper() {
        return (rs, rowNum) -> {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            product.setImageUrl(rs.getString("imageUrl"));
            return product;
        };
    }
}
