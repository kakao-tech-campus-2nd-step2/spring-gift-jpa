package gift.repository.dao;

import gift.controller.dto.request.ProductRequest;
import gift.model.Product;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProductDao {
    private final JdbcClient jdbcClient;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ProductDao(JdbcClient jdbcClient, DataSource dataSource) {
        this.jdbcClient = jdbcClient;
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");
    }

    public void updateById(Long id, ProductRequest request) {
        var sql = "update product set name = ?, price = ?, image_url = ? where id = ?";
        jdbcClient.sql(sql)
                .params(request.name(), request.price(), request.imageUrl(), id)
                .update();
    }

    public Long save(ProductRequest request) {
        Map<String, Object> params = new HashMap<>();
        params.put("name", request.name());
        params.put("price", request.price());
        params.put("image_url", request.imageUrl());
        params.put("created_at", LocalDateTime.now());
        params.put("updated_at", LocalDateTime.now());

        return simpleJdbcInsert.executeAndReturnKey(params).longValue();
    }

    public Optional<Product> findById(Long id) {
        var sql = "select * from product where id = ?";
        return jdbcClient.sql(sql)
                .params(id)
                .query(Product.class)
                .optional();
    }


    public List<Product> findAll() {
        var sql = "select * from product order by created_at";
        return jdbcClient.sql(sql)
                .query(Product.class)
                .list();
    }

    public void deleteById(Long id) {
        var sql = "delete from product where id = ?";
        jdbcClient.sql(sql)
                .params(id)
                .update();
    }

    public boolean existsById(Long id) {
        var sql = "select count(*) from product where id = ?";
        int count = jdbcClient.sql(sql)
                .params(id)
                .query(Integer.class)
                .single();
        return count > 0;
    }
}
