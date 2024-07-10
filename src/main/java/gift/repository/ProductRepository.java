package gift.repository;

import gift.DTO.Product;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final String INSERT_PRODUCT_SQL = "INSERT INTO product (id, name, price, image_url) VALUES (?, ?, ?, ?)";
    private static final String SELECT_ALL_PRODUCTS_SQL = "SELECT * FROM product";
    private static final String SELECT_PRODUCT_BY_ID_SQL = "SELECT * FROM product WHERE id = ?";
    private static final String UPDATE_PRODUCT_SQL = "UPDATE product SET name = ?, price = ?, image_url = ? WHERE id = ?";
    private static final String DELETE_PRODUCT_SQL = "DELETE FROM product WHERE id = ?";
    private static final String SELECT_PRODUCTS_BY_IDS_SQL = "SELECT * FROM product WHERE id IN (%s)";

    public List<Product> getAllProducts() {
        return jdbcTemplate.query(SELECT_ALL_PRODUCTS_SQL, new ProductRowMapper());
    }

    public List<Product> getAllProductsByIds(List<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        String inSql = String.join(",", ids.stream().map(String::valueOf)
            .collect(Collectors.toList()));
        String sql = String.format(SELECT_PRODUCTS_BY_IDS_SQL, inSql);
        return jdbcTemplate.query(sql, new ProductRowMapper());
    }

    public Optional<Product> getProductById(Long id) {
        try {
            return Optional.ofNullable(
                jdbcTemplate.queryForObject(SELECT_PRODUCT_BY_ID_SQL, new ProductRowMapper(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void addProduct(Product product) {
        jdbcTemplate.update(INSERT_PRODUCT_SQL, product.getId(), product.getName(),
            product.getPrice(), product.getImageUrl());
    }

    public void updateProduct(Product product) {
        jdbcTemplate.update(UPDATE_PRODUCT_SQL, product.getName(), product.getPrice(),
            product.getImageUrl(), product.getId());
    }

    public void deleteProduct(Long id) {
        jdbcTemplate.update(DELETE_PRODUCT_SQL, id);
    }

    private static class ProductRowMapper implements RowMapper<Product> {

        @Override
        public Product mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Product(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("image_url")
            );
        }
    }
}
