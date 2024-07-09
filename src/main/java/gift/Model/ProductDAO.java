package gift.Model;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    public ProductDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public void createProductTable(){
        var sql = """
                create table product(
                    id bigint PRIMARY KEY,
                    name varchar(255),
                    price int,
                    imageurl varchar(255)
                )
                """;
        jdbcTemplate.execute(sql);
    }

    public void insertProduct(Product product){
        var sql = "insert into product (id, name, price, imageUrl) values (?,?,?,?)";
        jdbcTemplate.update(sql, product.id(), product.name(), product.price(), product.imageUrl());
    }

    public void deleteProduct(Long id){
        var sql = "delete from product where id=?";
        jdbcTemplate.update(sql, id);
    }

    public void updateProduct(Long id, Product product){
        var sql = "update product set name=?, price=?, imageUrl=? where id =?";
        jdbcTemplate.update(sql,product.name(),product.price(),product.imageUrl(),id);
    }

    public List<Product> selectAllProduct(){
        var sql = "select id, name, price, imageUrl from product";
        List<Product> products = jdbcTemplate.query(
                sql, (resultSet, rowNum) -> {
                    Product product = new Product(
                            resultSet.getLong("id"),
                            resultSet.getString("name"),
                            resultSet.getInt("price"),
                            resultSet.getString("imageUrl")
                    );
                    return product;
                });
        return products;
    }

    public Product selectProduct(long id){
        var sql = "select id, name, price, imageUrl from product where id=?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl")
                ),
                id
        );
    }

    public Product selectProductByName(String name){
        var sql = "select id, name, price, imageUrl from product where name=?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new Product(
                        resultSet.getLong("id"),
                        resultSet.getString("name"),
                        resultSet.getInt("price"),
                        resultSet.getString("imageUrl")
                ),
                name
        );
    }
}
