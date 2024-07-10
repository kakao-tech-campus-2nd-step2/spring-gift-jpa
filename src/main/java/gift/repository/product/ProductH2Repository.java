package gift.repository.product;

import gift.domain.Product;
import java.util.List;
import java.util.Optional;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ProductH2Repository implements ProductRepository{
    private final JdbcTemplate jdbcTemplate;

    public ProductH2Repository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
        rs.getLong("id"),
        rs.getString("name"),
        rs.getInt("price"),
        rs.getString("imageUrl")
    );

    @Override
    public Optional<Product> findById(Long id) {
        var sql = "select * from product where id = ?";
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, productRowMapper, id));
    }

    @Override
    public List<Product> findAll() {
        var sql = "select * from product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public Product save(Product product) {
        var sql = "insert into product (name, price, imageUrl) values (?,?,?)";
        jdbcTemplate.update(sql,
            product.getName(), product.getPrice(), product.getImageUrl()
            );
        return product;
    }

    @Override
    public void update(Long id, Product product) {
        var sql = "update product set name = ?, price = ?, imageUrl = ? where id = ?";
        jdbcTemplate.update(sql, product.getName(), product.getPrice(), product.getImageUrl(), id);

    }
    @Override
    public void deleteById(Long id) {
        var sql = "delete from product where id =?";
        jdbcTemplate.update(sql, id);

    }

//    @Override
//    public void orderId() {
//        var sql = "select * from product order by id";
//        var sqlorder = "insert into product (id, name, price, imageUrl) values (?, ?, ?, ?)";
//        List<Product> productList = jdbcTemplate.query(sql, productRowMapper);
//        jdbcTemplate.update("alter table product alter column id restart with 1");
//        for(Product product : productList) {
//            jdbcTemplate.update(sqlorder, product.getId(), product.getName(), product.getPrice(), product.getImageUrl());
//        }
//
//    }
}
