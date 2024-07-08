package gift.repository;

import gift.dto.ProductRequestDTO;
import gift.model.Product;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepository {
    private final JdbcTemplate jdbcTemplate;

    private static final String productInsertSql = "INSERT INTO PRODUCTS (name, price, imageurl) VALUES (?, ?, ?)";
    private static final String allProductsSelectSql = "SELECT * FROM PRODUCTS";
    private static final String productUpdateSql = "UPDATE PRODUCTS SET name = ?, price = ?, imageurl = ? WHERE id = ?";
    private static final String productDeleteSql = "DELETE FROM PRODUCTS WHERE id = ?";

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertToTable(ProductRequestDTO productRequestDTO) {
        jdbcTemplate.update(productInsertSql, productRequestDTO.name(),
                                              productRequestDTO.price(),
                                              productRequestDTO.imageUrl());
    }

    public List<Product> selectAllProducts() {
        return jdbcTemplate.query(
                allProductsSelectSql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageurl")
                )
        );
    }

    public void updateToTable(Long id, ProductRequestDTO productRequestDTO) {
        jdbcTemplate.update(productUpdateSql, productRequestDTO.name(),
                                              productRequestDTO.price(),
                                              productRequestDTO.imageUrl(),
                                              id);
    }

    public void deleteToTable(Long id) {
        jdbcTemplate.update(productDeleteSql, id);
    }
}
