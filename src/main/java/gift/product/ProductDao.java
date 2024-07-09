package gift.product;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;

@Repository
public class ProductDao {
    @Autowired
    private JdbcClient jdbcClient;
    private JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcClient jdbcClient, JdbcTemplate jdbcTemplate) {
        this.jdbcClient = jdbcClient;
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

//        List<Product> products = jdbcClient.sql(sql).query(Product.class).list();
//        return products;
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

        //return jdbcClient.sql(sql).param(id).query(Product.class).optional();
    }

//    public List<Product> findProductById(List<Long> id) {
//        var sql = """
//            SELECT
//              id,
//              name,
//              price,
//              url
//            FROM product
//            WHERE id = ?
//            """;
//        return jdbcClient.sql(sql).param(id).query(Product.class).list();
//    }


    public void addProduct(Product product) {
        String sql = """
            INSERT INTO product (id, name, price, url)
            VALUES (?,?,?,?)
            """;

//        jdbcClient.sql(sql)
//            .param(product.getId())
//            .param(product.getName())
//            .param(product.getPrice())
//            .param(product.getUrl())
//            .update();
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

//        return jdbcClient.sql(sql)
//            .param(productRequestDto.name())
//            .param(productRequestDto.price())
//            .param(productRequestDto.url())
//            .param(id)
//            .update();
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
//        jdbcClient.sql(sql)
//            .param(id)
//            .update();
        jdbcTemplate.update(sql,id);
    }
}

