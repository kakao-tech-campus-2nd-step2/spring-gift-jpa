package gift.repository;

import gift.domain.Product;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;

//@Repository
public class ProductDao {
    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertProduct(Product product){
        var sql = "insert into product (id, name, price, imageUrl) values (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
    }

    public Product selectProduct(Long id){
        var sql = "select id, name, price, imageUrl from product where id = ?";
        Product product = jdbcTemplate.queryForObject(
            sql,
            (resultSet, rowNum) ->
                new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("imageUrl")
                ),
            id
        );
        return product;
    }

    public List<Product> selectAllProduct(){
        var sql = "select id, name, price, imageUrl from product";
        List<Product> productList = jdbcTemplate.query(
            sql,
            (resultSet, rowNum) ->
                new Product(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getInt("price"),
                    resultSet.getString("imageUrl")
                )
        );
        return productList;
    }
}