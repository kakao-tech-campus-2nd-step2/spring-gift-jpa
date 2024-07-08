package gift.api.product;

import java.sql.Types;
import java.util.List;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class ProductDao {

    private final JdbcClient jdbcClient;

    public ProductDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public List<Product> getAllProducts() {
        return jdbcClient.sql("select * from product")
                    .query(Product.class)
                    .list();
    }

    public Long insert(ProductRequest productRequest) {
        jdbcClient.sql("insert into product (name, price, imageUrl) values (:name, :price, :imageUrl)")
            .param("name", productRequest.getName(), Types.VARCHAR)
            .param("price", productRequest.getPrice(), Types.INTEGER)
            .param("imageUrl", productRequest.getImageUrl(), Types.VARCHAR)
            .update();
        return jdbcClient.sql("select id from product where name = :name")
            .param("name", productRequest.getName(), Types.VARCHAR)
            .query(Product.class)
            .single()
            .getId();
    }

    public void update(long id, ProductRequest productRequest) {
        jdbcClient.sql("update product set name = :name, price = :price, imageUrl = :imageUrl where id = :id")
            .param("name", productRequest.getName(), Types.VARCHAR)
            .param("price", productRequest.getPrice(), Types.INTEGER)
            .param("imageUrl", productRequest.getImageUrl(), Types.VARCHAR)
            .param("id", id, Types.BIGINT)
            .update();
    }

    public void delete(long id) {
        jdbcClient.sql("delete from product where id = :id")
            .param("id", id)
            .update();
    }
}
