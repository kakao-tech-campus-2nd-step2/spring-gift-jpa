package gift.repository;

import gift.model.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> selectAllProduct() {
        return jdbcTemplate.query(
            ProductQuery.SELECT_ALL_PRODUCT.getQuery(),
            (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
            )
        );
    }

    public Product selectProductById(Long id) {
        return jdbcTemplate.queryForObject(
            ProductQuery.SELECT_PRODUCT_BY_ID.getQuery(),
            (resultSet, rowNum) -> new Product(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getInt("price"),
                resultSet.getString("image_url")
            ),
            id
        );
    }

    public void insertProduct(Product product) {
        jdbcTemplate.update(ProductQuery.INSERT_PRODUCT.getQuery(),
            product.getName(), product.getPrice(), product.getImageUrl());
    }

    public void updateProductById(Long id, Product product) {
        jdbcTemplate.update(ProductQuery.UPDATE_PRODUCT_BY_ID.getQuery(), product.getName(),
            product.getPrice(), product.getImageUrl(), id);
    }

    public void deleteProductById(Long id) {
        jdbcTemplate.update(ProductQuery.DELETE_PRODUCT_BY_ID.getQuery(), id);
    }
}
