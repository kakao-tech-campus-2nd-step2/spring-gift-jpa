package gift.domain;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.NoSuchElementException;

@Repository
public class ProductDAO {
    private final JdbcTemplate jdbcTemplate;

    public ProductDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create() {
        var sql = """
            create table product (
                id bigint not null,
                name varchar(255) not null,
                price bigint not null,
                imageURL varchar(255) not null,
                primary key (id)
                )
            """;
        jdbcTemplate.execute(sql);
    }

    public void insert(Product product) {
        var sql = "INSERT INTO product (id, name, price, imageURL) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, product.id(), product.name(), product.price(), product.imageURL());
    }

    public Product select(long id) {
        var sql = "select * from product where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                new Object[]{id}, (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("imageURL")
                ));
    }

    public void delete(long id) {
        var sql = "delete from product where id = ?";
        try {
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new NoSuchElementException();
        }
    }

    public void update(long id, Product product) {
        var sql = "update product set name = ?, price = ?, imageURL = ? where id = ?";
        jdbcTemplate.update(sql, product.name(), product.price(), product.imageURL(), id);
    }

    public List<Product> selectAll() {
        var sql = "select * from product";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new Product(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getString("imageURL")
                ));
    }
}
