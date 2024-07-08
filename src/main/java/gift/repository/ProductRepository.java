package gift.repository;

import gift.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;


@Repository
public class ProductRepository {
    private final JdbcClient jdbcClient;

    public ProductRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public Long insertProduct(Product product){
        String sql = "insert into product (name, price, imageUrl) values (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcClient.sql(sql)
            .params(product.getName(), product.getPrice(), product.getImageUrl())
            .update(keyHolder);

        return keyHolder.getKey().longValue();
    }

    public Optional<Product> selectProduct(Long id){
        String sql = "select id, name, price, imageUrl from product where id = ?";
        return jdbcClient.sql(sql)
            .param(id)
            .query(Product.class)
            .optional();
    }

    public List<Product> selectAllProduct(){
        String sql = "select id, name, price, imageUrl from product";
        return jdbcClient.sql(sql)
            .query(Product.class)
            .list();
    }

    public void updateProduct(Product product){
        String sql = "update product set name = ?, price = ?, imageUrl = ? where id = ?";
        jdbcClient.sql(sql)
            .params(product.getName(), product.getPrice(), product.getImageUrl(), product.getId())
            .update();
    }

    public void deleteProduct(Long id){
        String sql = "delete from product where id = ?";
        jdbcClient.sql(sql)
            .param(id)
            .update();
    }
}


