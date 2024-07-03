package gift.product.repository;

import gift.product.domain.Product;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Repository
public class ProductRepository {
    private final JdbcClient jdbcClient;

    public ProductRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Product> findAll() {
        String sql = "select * from products";
        return jdbcClient.sql(sql)
                .query(Product.class)
                .list();
    }

    public Optional<Product> findById(Long id) {
        String sql = "select * from products where id = ?";
        return jdbcClient.sql(sql)
                .param(id)
                .query(Product.class)
                .optional();
    }

    public void save(Product product) {
        Assert.notNull(product, "Product must not be null");
        if (product.checkNew()) {
            String sql = "INSERT INTO products (name, price, image_url) VALUES (?, ?, ?)";
            jdbcClient.sql(sql)
                    .param(product.getName())
                    .param(product.getPrice())
                    .param(product.getImageUrl())
                    .update();

        }
        if (!product.checkNew()) {
            String sql = "UPDATE products SET name = ?, price = ?, image_url = ? WHERE id = ?";
            jdbcClient.sql(sql)
                    .param(product.getName())
                    .param(product.getPrice())
                    .param(product.getImageUrl())
                    .param(product.getId())
                    .update();
        }
    }

    public void deleteById(Long id) {
        String sql = "delete from products where id = ?";
        jdbcClient.sql(sql)
                .param(id)
                .update();
    }
}
