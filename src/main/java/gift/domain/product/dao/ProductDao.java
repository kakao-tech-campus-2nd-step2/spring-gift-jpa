package gift.domain.product.dao;

import gift.domain.product.dto.ProductDto;
import gift.domain.product.entity.Product;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcClient jdbcClient;

    public ProductDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Product insert(Product product) {
        String sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
            .param(product.getName())
            .param(product.getPrice())
            .param(product.getImageUrl())
            .update(keyHolder);

        product.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());

        return product;
    }

    public List<Product> findAll() {
        String sql = "SELECT * FROM product";

        return jdbcClient.sql(sql)
            .query(ProductDto.class)
            .stream()
            .map(ProductDto::toProduct)
            .toList();
    }

    public Optional<Product> findById(long productId) {
        String sql = "SELECT * FROM product WHERE id = ?";

        Optional<ProductDto> productDto = jdbcClient.sql(sql)
            .param(productId)
            .query(ProductDto.class)
            .optional();

        if (productDto.isEmpty()) {
            return Optional.empty();
        }
        return productDto.map(ProductDto::toProduct);
    }

    public Optional<Product> update(Product product) {
        String sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";

        int nOfRowsAffected = jdbcClient.sql(sql)
            .param(product.getName())
            .param(product.getPrice())
            .param(product.getImageUrl())
            .param(product.getId())
            .update();

        if (nOfRowsAffected <= 0) {
            return Optional.empty();
        }

        return Optional.of(product);
    }

    public Integer delete(long productId) {
        String sql = "DELETE FROM product WHERE id = ?";

        return jdbcClient.sql(sql)
            .param(productId)
            .update();
    }
}