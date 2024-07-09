package gift.product.model;

import gift.product.model.dto.ProductResponse;
import gift.product.model.dto.UpdateProductRequest;
import gift.product.model.dto.CreateProductRequest;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ProductResponse findProduct(Long id) {
        var sql = "SELECT id, name, price, image_url FROM product WHERE id = ? AND is_active = true";
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> new ProductResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                ),
                id
        );
    }

    public List<ProductResponse> findAllProduct() {
        var sql = "SELECT id, name, price, image_url FROM product where is_active = true";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ProductResponse(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("image_url")
                )
        );
    }


    public int addProduct(CreateProductRequest createProductRequest) {
        var sql = "INSERT INTO product (name, price, image_url) VALUES (?, ?, ?)";
        Object[] params = new Object[]{createProductRequest.getName(), createProductRequest.getPrice(),
                createProductRequest.getImageUrl()};
        return jdbcTemplate.update(sql, params);
    }

    public int updateProduct(UpdateProductRequest updatedProduct) {
        var sql = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
        return jdbcTemplate.update(
                sql,
                updatedProduct.getName(),
                updatedProduct.getPrice(),
                updatedProduct.getImageUrl(),
                updatedProduct.getId()
        );
    }

    public int deleteProduct(Long id) {
        var sql = "UPDATE product SET is_active = false WHERE id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
