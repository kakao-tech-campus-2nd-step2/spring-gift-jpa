package gift.product;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductRepository {

    private final JdbcTemplate jdbcTemplate;

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void addProduct(Product product) {
        jdbcTemplate.update(
            "INSERT INTO PRODUCT(NAME, PRICE, IMAGEURL) VALUES(?, ?, ?)",
            product.name(), product.price(), product.imageUrl());
    }

    public List<Product> getAllProducts() {
        return jdbcTemplate.query(
            "SELECT * FROM PRODUCT",
            (rs, rowNum) -> new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("imageUrl")));
    }

    public void updateProduct(Product product) {
        jdbcTemplate.update(
            "UPDATE PRODUCT SET NAME = ?, PRICE = ?, IMAGEURL = ? WHERE ID = ?",
            product.name(), product.price(), product.imageUrl(), product.id());
    }

    public void deleteProduct(long id) {
        jdbcTemplate.update("DELETE FROM PRODUCT WHERE ID = ?", id);
    }

    public Boolean existProduct(long id) {
        return jdbcTemplate.queryForObject(
            "SELECT EXISTS (SELECT * FROM PRODUCT WHERE ID = ?)",
            Boolean.class,
            id
        );
    }
}