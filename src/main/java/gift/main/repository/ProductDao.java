package gift.main.repository;

import gift.main.entity.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "id BIGINT PRIMARY KEY," +
                "name VARCHAR(255) NOT NULL," +
                "price INT NOT NULL," +
                "image_url VARCHAR(255)" +
                ")";
        jdbcTemplate.execute(sql);
    }

    public List<Product> selectProductAll() {
        String sql = "SELECT * FROM products";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            int price = rs.getInt("price");
            String imageUrl = rs.getString("image_url");
            return new Product(id, name, price, imageUrl);
        });
    }

    public Product selectProduct(Long id) {
        String sql = "SELECT * FROM products WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                long productId = rs.getLong("id");
                String name = rs.getString("name");
                int price = rs.getInt("price");
                String imageUrl = rs.getString("image_url");
                return new Product(productId, name, price, imageUrl);
            }, id);
        } catch (EmptyResultDataAccessException e) {
            // 해당 id의 Product가 존재하지 않는 경우
            return null;
        }
    }

    public void insertProduct(Product product) {
        String sql = "INSERT INTO products (id, name, price, image_url) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void updateProduct(Long id, Product product) {
        String sql = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);
    }

    public void deleteProduct(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
