package gift.repository;

import gift.domain.Product;
import gift.exception.ProductNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepositoryImpl implements ProductRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductRepositoryImpl(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Product addProduct(Product product) {
        String sql = "INSERT INTO products (name, price, image_url) VALUES (?, ?, ?)";
        Object[] params = new Object[]{product.getName(), product.getPrice(), product.getImageUrl()};
        jdbcTemplate.update(sql, params);

        String selectSql = "SELECT id, name, price, image_url FROM products WHERE name = ? AND price = ? AND image_url = ?";
        return jdbcTemplate.queryForObject(selectSql, new Object[]{product.getName(), product.getPrice(), product.getImageUrl()},
            (result, rowNum) -> new Product(
                result.getLong("id"),
                result.getString("name"),
                result.getInt("price"),
                result.getString("image_url")
            ));
    }

    @Override
    public List<Product> findAll() {
        var sql = "SELECT id, name, price, image_url FROM products ";
        return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("image_url")
            )
        );
    }

    @Override
    public Product findById(Long id) throws ProductNotFoundException {
        var sql = "SELECT id, name, price, image_url FROM products WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new Product(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getInt("price"),
                    rs.getString("image_url")
                ),
                id
            );
        } catch (Exception e) {
            throw new ProductNotFoundException("Product not found");
        }
    }

    @Override
    public Product updateProduct(Long id, Product updateProduct) throws ProductNotFoundException {
        var sql = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?";
        int rows = jdbcTemplate.update(
            sql,
            updateProduct.getName(),
            updateProduct.getPrice(),
            updateProduct.getImageUrl(),
            id
        );

        if (rows == 0) {
            throw new ProductNotFoundException("Product not found");
        }

        return findById(id);
    }

    @Override
    public void deleteProduct(Long id) throws ProductNotFoundException {
        var sql = "DELETE products WHERE id = ?";
        int rows = jdbcTemplate.update(sql, id);

        if (rows == 0) {
            throw new ProductNotFoundException("Product not found");
        }
    }

}
