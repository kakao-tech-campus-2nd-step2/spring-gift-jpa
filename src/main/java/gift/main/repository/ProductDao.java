package gift.main.repository;

import gift.main.dto.ProductDto;
import gift.main.entity.Product;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createProductTable();
    }



    public void createProductTable() {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "id BIGINT AUTO_INCREMENT PRIMARY KEY," +
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

    public void insertProduct(ProductDto productDto) {

        String sql = "INSERT INTO products (name, price, image_url) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productDto.getName(), productDto.getPrice(), productDto.getImageUrl());
    }



    public void updateProduct(long id, ProductDto productDto) {
        String sql = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?";
        jdbcTemplate.update(sql, productDto.getName(), productDto.getPrice(), productDto.getImageUrl(), id);
    }

    public void deleteProduct(Long id) {
        String sql = "DELETE FROM products WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    public boolean existsProduct(long id) {
        String sql = "SELECT COUNT(*) FROM products WHERE id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }


}
