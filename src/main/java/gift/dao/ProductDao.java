package gift.dao;

import gift.model.product.Product;
import gift.model.product.ProductName;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class ProductDao {

    private final JdbcTemplate jdbcTemplate;

    public ProductDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void insertProduct(Product product) {
        var sql = "insert into products (id, productName, price, imageUrl, amount) values (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.getId(), product.getName().getName(), product.getPrice(),product.getImageUrl(), product.getAmount());
    }

    public void deleteProduct(long id) {
        var sql = "delete from products where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public void updateProduct(Product product){
        var sql = "update products set productName = ? , price = ?, imageUrl = ?, amount = ? where id = ? ";
        jdbcTemplate.update(sql, product.getName().getName(), product.getPrice(),product.getImageUrl(), product.getAmount(), product.getId());
    }

    public void purchaseProduct(long id, int amount){
        var sql = "update products set amount = amount - ? where id = ? ";
        jdbcTemplate.update(sql,amount, id);
    }

    public List<Product> selectAllProducts() {
        var sql = "select id, productName, price, imageUrl, amount from products";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        new ProductName(resultSet.getString("productName")),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl"),
                        resultSet.getInt("amount")
                )
        );
    }

    public Product selectProduct(long id) {
        var sql = "select id, productName, price, imageUrl, amount from products where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        new ProductName(resultSet.getString("productName")),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl"),
                        resultSet.getInt("amount")
                ),
                id
        );
    }

    public boolean isProductExist(long id) {
        var sql = "select count(*) from products where id = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        return count > 0;
    }
}
