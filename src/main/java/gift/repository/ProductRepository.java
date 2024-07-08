package gift.repository;

import gift.model.Product;
import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    private static final String TABLE_NAME = "products";
    private static final String FIELD_ID = "id";
    private static final String FIELD_NAME = "name";
    private static final String FIELD_PRICE = "price";
    private static final String FIELD_IMAGE_URL = "imageUrl";

    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<Product> productRowMapper = (resultSet, rowNum) ->
        new Product(
            resultSet.getLong(FIELD_ID),
            resultSet.getString(FIELD_NAME),
            resultSet.getString(FIELD_PRICE),
            resultSet.getString(FIELD_IMAGE_URL)
        );

    public ProductRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    public void init() {
        simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
            .withTableName(TABLE_NAME)
            .usingGeneratedKeyColumns(FIELD_ID);
    }

    public List<Product> findProductsAll() {
        String sql = "SELECT * FROM " + TABLE_NAME;
        return jdbcTemplate.query(sql, productRowMapper);
    }

    public Product findProductsById(long id) {
        String sql = "SELECT * FROM " + TABLE_NAME + " WHERE " + FIELD_ID + " = ?";
        List<Product> products = jdbcTemplate.query(sql, new Object[]{id}, productRowMapper);
        return products.isEmpty() ? null : products.get(0);
    }

    public Product saveProduct(Product product) {
        Map<String, Object> params = new HashMap<>();
        params.put(FIELD_NAME, product.name());
        params.put(FIELD_PRICE, product.price());
        params.put(FIELD_IMAGE_URL, product.imageUrl());

        Number newId = simpleJdbcInsert.executeAndReturnKey(params);
        return new Product(newId.longValue(), product.name(), product.price(), product.imageUrl());
    }

    public void deleteProduct(long id) {
        String sql = "DELETE FROM " + TABLE_NAME + " WHERE " + FIELD_ID + " = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateProduct(Product product, long id) {
        String sql = "UPDATE " + TABLE_NAME + " SET " + FIELD_ID + " = ?, " + FIELD_NAME + " = ?, "
            + FIELD_PRICE + " = ?, " + FIELD_IMAGE_URL + " = ? WHERE " + FIELD_ID + " = ?";
        jdbcTemplate.update(sql, product.id(), product.name(), product.price(), product.imageUrl(),
            id);
    }

}
