package gift.product;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class ProductDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Product> findAllProduct() {
        String sql = """
            SELECT * 
            FROM product
            """;

        RowMapper<Product> rowMapper = (rs, rowNum) -> new Product(
            rs.getString("name"),
            rs.getInt("price"),
            rs.getString("url")
        );

        return jdbcTemplate.query(sql,rowMapper);

    }

    public Optional<ProductResponseDto> findProductById(Long id) {
        String sql = """
            SELECT 
              id, 
              name, 
              price, 
              url
            FROM product
            WHERE id = ?
            """;

        ProductResponseDto productResponseDto = jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> new ProductResponseDto(
                rs.getLong("id"),
                rs.getString("name"),
                rs.getInt("price"),
                rs.getString("url")
            ),
            id
        );

        return Optional.ofNullable(productResponseDto);

    }

    public void addProduct(Product product) {
        String sql = """
            INSERT INTO product (id, name, price, url)
            VALUES (?,?,?,?)
            """;

        jdbcTemplate.update(sql,
            product.getId(),
            product.getName(),
            product.getPrice(),
            product.getUrl());
    }

    public Integer updateProductById(Long id, ProductRequestDto productRequestDto) {
        String sql = """
            UPDATE product
            SET name = ?, price = ?, url = ?
            WHERE id = ?
            """;

        return jdbcTemplate.update(sql,
            productRequestDto.name(),
            productRequestDto.price(),
            productRequestDto.url(),
            id);
    }
    public void deleteProductById(Long id) {
        var sql = """
            DELETE FROM product
            WHERE id = ?
            """;

        jdbcTemplate.update(sql,id);
    }
}

