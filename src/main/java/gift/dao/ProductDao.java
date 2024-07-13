package gift.dao;

import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> getAllProducts() {

        String sql = "SELECT * FROM product";

        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Product getProductById(Long id) {

        String sql = "SELECT * FROM product WHERE id = ?";

        return jdbcTemplate.queryForObject(sql, new ProductRowMapper(), id);
    }

    public void addProduct(Product product) {

        String sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";

        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void updateProduct(Long id, Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }

    public void deleteProduct(Long id) {
        String sql = "DELETE FROM product WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private static class ProductRowMapper implements RowMapper<Product> {
        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            Product product = new Product();
            product.setId(rs.getLong("id"));
            product.setName(rs.getString("name"));
            product.setPrice(rs.getInt("price"));
            product.setImageUrl(rs.getString("image_url"));
            return product;
        }
    }
}
