package gift.product;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {
    @Autowired
    private JdbcClient jdbcClient;

    public ProductDao(JdbcClient jdbcClient) {this.jdbcClient = jdbcClient;}

    public List<Product> findAllProduct() {
        var sql = """
            SELECT * 
            FROM product
            """;
        List<Product> products = jdbcClient.sql(sql).query(Product.class).list();
        return products;
    }

    public Optional<Product> findProductById(Long id) {
        var sql = """
            SELECT 
              id, 
              name, 
              price, 
              url
            FROM product
            WHERE id = ?
            """;
        return jdbcClient.sql(sql).param(id).query(Product.class).optional();
    }

    public List<Product> findProductById(List<Long> id) {
        var sql = """
            SELECT 
              id, 
              name,
              price, 
              url
            FROM product
            WHERE id = ?
            """;
        return jdbcClient.sql(sql).param(id).query(Product.class).list();
    }


    public void addProduct(Product product) {
        var sql = """
            INSERT INTO product (id, name, price, url)
            VALUES (?,?,?,?)
            """;

        jdbcClient.sql(sql)
            .param(product.getId())
            .param(product.getName())
            .param(product.getPrice())
            .param(product.getUrl())
            .update();
    }

    public Integer updateProductById(Long id, ProductRequestDto productRequestDto) {
        var sql = """
            UPDATE product
            SET name = ?, price = ?, url = ?
            WHERE id = ?
            """;

        return jdbcClient.sql(sql)
            .param(productRequestDto.name())
            .param(productRequestDto.price())
            .param(productRequestDto.url())
            .param(id)
            .update();
    }
    public void deleteProductById(Long id) {
        var sql = """
            DELETE FROM product
            WHERE id = ?
            """;
        jdbcClient.sql(sql)
            .param(id)
            .update();
    }
}

